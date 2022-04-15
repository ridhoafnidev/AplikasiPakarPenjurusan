package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceAbsensi
import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceUser
import com.kemenag_inhu.absensi.core_data.data.local.entity.toDomain
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.AbsensiService
import com.kemenag_inhu.absensi.core_domain.model.ListDataAbsensi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AbsensiRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: AbsensiService,
    private val localDataSourceUser: LocalDataSourceUser,
    private val localDataSourceAbsensi: LocalDataSourceAbsensi,
    ) {

    //region Local Data Source Operation

    fun getById(id: Int) = localDataSourceAbsensi.selectByIdAsFlow(id)
        .map { it?.toDomain() }
        .map { ApiEvent.OnSuccess.fromCache(it) }

    //endregion
    //region Remote Data Source Operation

    fun loadHistoryAbsensi(
        id: Int? = null
    ): Flow<ApiEvent<ListDataAbsensi?>> =
        flow {
            runCatching {

                val user = localDataSourceUser.selectCurrentUser()?.toDomain()
                val userId = requireNotNull(id ?: user?.idUser)

                val apiId = AbsensiService.GetBy
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getBy(id ?: userId)
                }

                val apiEvent: ApiEvent<ListDataAbsensi?> = when (apiResult) {
                    is ApiResult.OnFailed -> apiResult.exception.toFailedEventNullable<ListDataAbsensi>()
                    is ApiResult.OnSuccess -> with(apiResult.response) {
                        if (status == ApiException.FailedResponse.STATUS_FAIL) {
                            with(ApiException.FailedResponse(message)) {
                                apiExecutor.logException(apiId, this)
                                toFailedEventNullable<ListDataAbsensi>()
                            }
                        } else toDomain()?.run {
                            localDataSourceAbsensi.inserts(toEntity())
                            ApiEvent.OnSuccess.fromServer<ListDataAbsensi?>(this)
                        } ?: with(ApiException.NullResponse) {
                            apiExecutor.logException(apiId, this)
                            toFailedEventNullable<ListDataAbsensi>()
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventNullable<ListDataAbsensi>())
            }
        }

    //endregion

}
