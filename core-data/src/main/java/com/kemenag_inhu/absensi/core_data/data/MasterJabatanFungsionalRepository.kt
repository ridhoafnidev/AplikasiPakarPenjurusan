package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceMasterJabatanFungsional
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterJabatanFungsionalService
import com.kemenag_inhu.absensi.core_domain.model.ListMasterJabatanFungsional
import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanFungsional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MasterJabatanFungsionalRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: MasterJabatanFungsionalService,
    private val localDataSourse: LocalDataSourceMasterJabatanFungsional,
    ) {

    //region Remote Data Source Operation

    fun getAll(): Flow<ApiEvent<ListMasterJabatanFungsional>> =
        flow {
            runCatching {

                val apiId = MasterJabatanFungsionalService.GetAll
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getAll()
                }

                val apiEvent: ApiEvent<ListMasterJabatanFungsional> = when (apiResult) {
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

                                if (localDataSourse.deleteAll() == 0) {
                                    ApiEvent.OnSuccess.fromCache(this, Int.MAX_VALUE)
                                } else ApiEvent.OnSuccess.fromServer(this)
                            }
                            else -> toDomain().run {
                                localDataSourse.replaceAll(toEntity())
                                ApiEvent.OnSuccess.fromServer(this)
                            }
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventEmptyList<MasterJabatanFungsional>())
            }
        }

    //endregion

}
