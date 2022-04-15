package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceMasterPnsNonpns
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterPnsNonpnsService
import com.kemenag_inhu.absensi.core_domain.model.ListMasterPnsNonpns
import com.kemenag_inhu.absensi.core_domain.model.MasterPnsNonpns
import com.kemenag_inhu.absensi.core_util.api.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MasterPnsNonpnsRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: MasterPnsNonpnsService,
    private val localDataSourceMasterPnsNonpns: LocalDataSourceMasterPnsNonpns,
    ) {

    //region Remote Data Source Operation

    fun getAll(): Flow<ApiEvent<ListMasterPnsNonpns>> =
        flow {
            runCatching {

                val apiId = MasterPnsNonpnsService.GetAll
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getAll()
                }

                val apiEvent: ApiEvent<ListMasterPnsNonpns> = when (apiResult) {
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

                                if (localDataSourceMasterPnsNonpns.deleteAll() == 0) {
                                    ApiEvent.OnSuccess.fromCache(this, Int.MAX_VALUE)
                                } else ApiEvent.OnSuccess.fromServer(this)
                            }
                            else -> toDomain().run {
                                localDataSourceMasterPnsNonpns.replaceAll(toEntity())
                                ApiEvent.OnSuccess.fromServer(this)
                            }
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventEmptyList<MasterPnsNonpns>())
            }
        }

    //endregion

}
