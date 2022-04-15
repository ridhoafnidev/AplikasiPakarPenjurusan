package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceMasterJabatanStruktural
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterJabatanStrukturalService
import com.kemenag_inhu.absensi.core_domain.model.ListMasterJabatanStruktural
import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanStruktural
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MasterJabatanStrukturalRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: MasterJabatanStrukturalService,
    private val localDataSourseMasterJabatanStruktural: LocalDataSourceMasterJabatanStruktural,
    ) {

    //region Remote Data Source Operation

    fun getAll(): Flow<ApiEvent<ListMasterJabatanStruktural>> =
        flow {
            runCatching {

                val apiId = MasterJabatanStrukturalService.GetAll
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getAll()
                }

                val apiEvent: ApiEvent<ListMasterJabatanStruktural> = when (apiResult) {
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

                                if (localDataSourseMasterJabatanStruktural.deleteAll() == 0) {
                                    ApiEvent.OnSuccess.fromCache(this, Int.MAX_VALUE)
                                } else ApiEvent.OnSuccess.fromServer(this)
                            }
                            else -> toDomain().run {
                                localDataSourseMasterJabatanStruktural.replaceAll(toEntity())
                                ApiEvent.OnSuccess.fromServer(this)
                            }
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventEmptyList<MasterJabatanStruktural>())
            }
        }

    //endregion

}
