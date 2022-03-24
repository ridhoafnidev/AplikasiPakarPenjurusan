package com.example.core_data.repository

import com.example.core_data.api.*
import com.example.core_data.api.ApiExecutor
import com.example.core_data.api.ApiResult
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.api.response.toDomain
import com.example.core_data.api.service.AuthService
import com.example.core_data.api.service.UserService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.User
import com.example.core_data.domain.Users
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
    private val jsonParser: Moshi,
) {

    fun getCurrentUserAsFlow() = dao.selectAuthAsFlow().map { auth ->
        ApiEvent.OnSuccess.fromCache(auth?.toDomain())
    }

    suspend fun getAuth() = dao.selectAuth()
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

}