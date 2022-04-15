package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceMasterLevel
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterLevelService
import com.kemenag_inhu.absensi.core_domain.model.ListMasterLevel
import com.kemenag_inhu.absensi.core_domain.model.MasterLevel
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MasterLevelRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: MasterLevelService,
    private val localDataSourseMasterLevel: LocalDataSourceMasterLevel,
    ) {

    //region Remote Data Source Operation

    fun getAll(): Flow<ApiEvent<ListMasterLevel>> =
        flow {
            runCatching {

                val apiId = MasterLevelService.GetAll
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getAll()
                }

                val apiEvent: ApiEvent<ListMasterLevel> = when (apiResult) {
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

                                if (localDataSourseMasterLevel.deleteAll() == 0) {
                                    ApiEvent.OnSuccess.fromCache(this, Int.MAX_VALUE)
                                } else ApiEvent.OnSuccess.fromServer(this)
                            }
                            else -> toDomain().run {
                                localDataSourseMasterLevel.replaceAll(toEntity())
                                ApiEvent.OnSuccess.fromServer(this)
                            }
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventEmptyList<MasterLevel>())
            }
        }

    //endregion

}
