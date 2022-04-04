package com.example.core_data.repository

import com.example.core_data.api.*
import com.example.core_data.api.request.guru.RegisterGuruRequest
import com.example.core_data.api.request.siswa.RegisterSiswaRequest
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.api.response.guru.toDomain
import com.example.core_data.api.response.siswa.toDomain
import com.example.core_data.api.response.toDomain
import com.example.core_data.api.service.UserService
import com.example.core_data.domain.*
import com.example.core_data.persistence.dao.GuruDao
import com.example.core_data.persistence.dao.SiswaDao
import com.example.core_data.persistence.dao.UserDao
import com.example.core_data.persistence.entity.toDomain
import com.example.core_data.persistence.entity.toEntity
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AuthRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val authService: UserService,
    private val dao: UserDao,
    private val guruDao: GuruDao,
    private val siswaDao: SiswaDao,
    private val jsonParser: Moshi,
) {

    fun getCurrentUserAsFlow() = dao.selectAuthAsFlow().map { auth ->
        ApiEvent.OnSuccess.fromCache(auth?.toDomain())
    }

    suspend fun getAuth() = dao.selectAuth()
        ?.toDomain()

    suspend fun getGuruDetail(idUser: Int) = guruDao.selectGuruById(idUser)
        ?.toDomain()

    suspend fun getSiswaDetail(idUser: Int) = siswaDao.selectSiswaById(idUser)
        ?.toDomain()

    fun login(
        username: String,
        password: String
    ): Flow<ApiEvent<User>> = flow {
        runCatching {
            val apiId = UserService.Login

            val apiResult = apiExecutor.callApi(apiId) {
                authService.login(username, password)
            }

            val apiEvent: ApiEvent<User> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()

                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    dao.replace(
                        toDomain().toEntity().copy(isCurrent = true)
                    )
                    ApiEvent.OnSuccess.fromServer(this.toDomain())
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<User>())
        }
    }

    fun changePassword(
        idUser: Int,
        oldPassword: String,
        newPassword: String,
    ): Flow<ApiEvent<CommonResponse?>> = flow {
        runCatching {
            val apiId = UserService.ChangePassword

            val apiResult = apiExecutor.callApi(apiId) {
                authService.changePassword(idUser, oldPassword, newPassword)
            }

            val apiEvent: ApiEvent<CommonResponse?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response) {
                    when {
                        message.equals(ApiException.FailedResponse.STATUS_FAILED, true) -> {
                            ApiException.FailedResponse(message).let {
                                it.toFailedEvent()
                            }
                        }
                        else -> ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<CommonResponse>())
        }
    }

    fun getGuruById(idUser: Int): Flow<ApiEvent<Guru?>> = flow {
        runCatching {
            val apiId = UserService.GetUserById

            val apiResult = apiExecutor.callApi(apiId) {
                authService.getGuruById(idUser)
            }

            val apiEvent: ApiEvent<Guru?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        guruDao.replaceById(idUser, this.toEntity())
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }

            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<Guru>())
        }
    }

    fun getSiswaById(idUser: Int): Flow<ApiEvent<Siswa?>> = flow {
        runCatching {
            val apiId = UserService.GetUserById

            val apiResult = apiExecutor.callApi(apiId) {
                authService.getSiswaById(idUser)
            }

            val apiEvent: ApiEvent<Siswa?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        siswaDao.replaceById(idUser, this.toEntity())
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }

            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<Siswa>())
        }
    }

    fun registerGuru(
        registerGuruRequest: RegisterGuruRequest
    ): Flow<ApiEvent<RegisterGuru>> = flow {
        runCatching {
            val apiId = UserService.Register

            val apiResult = apiExecutor.callApi(apiId) {
                authService.registerGuru(registerGuruRequest)
            }

            val apiEvent: ApiEvent<RegisterGuru> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()

                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    ApiEvent.OnSuccess.fromServer(this.toDomain())
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<RegisterGuru>())
        }
    }

    fun registerSiswa(
        registerSiswaRequest: RegisterSiswaRequest
    ): Flow<ApiEvent<RegisterSiswa>> = flow {
        runCatching {
            val apiId = UserService.Register

            val apiResult = apiExecutor.callApi(apiId) {
                authService.registerSiswa(registerSiswaRequest)
            }

            val apiEvent: ApiEvent<RegisterSiswa> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()

                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    ApiEvent.OnSuccess.fromServer(this.toDomain())
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<RegisterSiswa>())
        }
    }

}