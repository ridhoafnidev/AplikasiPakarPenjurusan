package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceMasterJabatanStruktural
import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceMasterJenisTenaga
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterJabatanStrukturalService
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterJenisTenagaService
import com.kemenag_inhu.absensi.core_domain.model.ListMasterJenisTenaga
import com.kemenag_inhu.absensi.core_domain.model.MasterJenisTenaga
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MasterJenisTenagaRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: MasterJenisTenagaService,
    private val localDataSourseMasterJenisTenaga: LocalDataSourceMasterJenisTenaga,
    ) {

    //region Remote Data Source Operation

    fun getAll(): Flow<ApiEvent<ListMasterJenisTenaga>> =
        flow {
            runCatching {

                val apiId = MasterJenisTenagaService.GetAll
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getAll()
                }

                val apiEvent: ApiEvent<ListMasterJenisTenaga> = when (apiResult) {
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

                                if (localDataSourseMasterJenisTenaga.deleteAll() == 0) {
                                    ApiEvent.OnSuccess.fromCache(this, Int.MAX_VALUE)
                                } else ApiEvent.OnSuccess.fromServer(this)
                            }
                            else -> toDomain().run {
                                localDataSourseMasterJenisTenaga.replaceAll(toEntity())
                                ApiEvent.OnSuccess.fromServer(this)
                            }
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventEmptyList<MasterJenisTenaga>())
            }
        }

    //endregion

}
