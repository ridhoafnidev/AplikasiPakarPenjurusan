package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourcePegawai
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterPegawaiService
import com.kemenag_inhu.absensi.core_domain.model.ListPegawai
import com.kemenag_inhu.absensi.core_domain.model.Pegawai
import com.kemenag_inhu.absensi.core_util.api.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MasterPegawaiRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: MasterPegawaiService,
    private val localDataSourcePegawai: LocalDataSourcePegawai,
    ) {

    //region Remote Data Source Operation

    fun getAll(): Flow<ApiEvent<ListPegawai>> =
        flow {
            runCatching {

                val apiId = MasterPegawaiService.GetAll
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getAll()
                }

                val apiEvent: ApiEvent<ListPegawai> = when (apiResult) {
                    is ApiResult.OnFailed -> apiResult.exception.toFailedEventEmptyList()
                    is ApiResult.OnSuccess -> with(apiResult.response) {
                        when {
                            status == ApiException.FailedResponse.STATUS_FAIL -> {
                                with(ApiException.FailedResponse(message)) {
                                    apiExecutor.logException(apiId, this)
                                    toFailedEventEmptyList()
                                }
                            }
                            data.isEmpty() -> toDomain().run {
                                apiExecutor.logException(apiId, ApiException.EmptyResponse)

                                if (localDataSourcePegawai.deleteAll() == 0) {
                                    ApiEvent.OnSuccess.fromCache(this, Int.MAX_VALUE)
                                } else ApiEvent.OnSuccess.fromServer(this)
                            }
                            else -> toDomain().run {
                                localDataSourcePegawai.replaceAll(toEntity())
                                ApiEvent.OnSuccess.fromServer(this)
                            }
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventEmptyList<Pegawai>())
            }
        }

    //endregion

}
