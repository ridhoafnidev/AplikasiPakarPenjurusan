package com.example.core_data.repository

import com.example.core_data.api.ApiEvent
import com.example.core_data.api.ApiExecutor
import com.example.core_data.api.ApiResult
import com.example.core_data.api.request.nilai_siswa.AddNilaiSiswaRequest
import com.example.core_data.api.response.nilai_siswa.toDomain
import com.example.core_data.api.service.NilaiSiswaService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.ListNilaiSiswa
import com.example.core_data.domain.NilaiSiswa
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NilaiSiswaRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val nilaiSiswaService: NilaiSiswaService,
    private val jsonParser: Moshi,
) {
    fun getNilaiSiswaAll(): Flow<ApiEvent<ListNilaiSiswa?>> = flow {
        runCatching {
            val apiId = NilaiSiswaService.GetNilaiSiswaAll

            val apiResult = apiExecutor.callApi(apiId) {
                nilaiSiswaService.getNilaiSiswaAll()
            }

            val apiEvent: ApiEvent<ListNilaiSiswa?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        if (isEmpty()) {
                            ApiEvent.OnSuccess.fromServer(emptyList())
                        } else {
                            ApiEvent.OnSuccess.fromServer(this)
                        }
                    }
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<ListNilaiSiswa>())
        }
    }

    fun deleteNilaiSiswa(idUser: Int): Flow<ApiEvent<Unit?>> = flow {
        runCatching {
            val apiId = NilaiSiswaService.DeleteNilaiSiswa

            val apiResult = apiExecutor.callApi(apiId) {
                nilaiSiswaService.deleteNilaiSiswaById(idUser)
            }

            val apiEvent: ApiEvent<Unit?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> ApiEvent.OnSuccess.fromServer(Unit)
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<Unit>())
        }
    }

    fun addNilaiSiswa(
        addNilaiSiswaRequest: AddNilaiSiswaRequest
    ): Flow<ApiEvent<NilaiSiswa?>> = flow {
        runCatching {
            val apiId = NilaiSiswaService.AddNilaiSiswa

            val apiResult = apiExecutor.callApi(apiId) {
                nilaiSiswaService.addNilaiSiswa(addNilaiSiswaRequest)
            }

            val apiEvent: ApiEvent<NilaiSiswa?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }

            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<NilaiSiswa?>())
        }
    }

    fun updateNilaiSiswa(
        idUser: Int,
        updateNilaiSiswaRequest: AddNilaiSiswaRequest
    ): Flow<ApiEvent<NilaiSiswa?>> = flow {
        runCatching {
            val apiId = NilaiSiswaService.UpdateNilaiSiswa

            val apiResult = apiExecutor.callApi(apiId) {
                nilaiSiswaService.updateNilaiSiswa(idUser, updateNilaiSiswaRequest)
            }

            val apiEvent: ApiEvent<NilaiSiswa?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }

            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<NilaiSiswa?>())
        }
    }
}