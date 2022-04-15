package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourcePegawaiProfile
import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSourceUser
import com.kemenag_inhu.absensi.core_data.data.local.entity.toDomain
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.MasterPegawaiService
import com.kemenag_inhu.absensi.core_domain.model.PegawaiProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PegawaiProfileRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: MasterPegawaiService,
    private val localDataSourceUser: LocalDataSourceUser,
    private val localDataSourcePegawaiProfile: LocalDataSourcePegawaiProfile,
    ) {

    //region Local Data Source Operation

    fun getProfileById(id: Int) = localDataSourcePegawaiProfile.selectByIdAsFlow(id)
        .map { it?.toDomain() }
        .map { ApiEvent.OnSuccess.fromCache(it) }

    //endregion
    //region Remote Data Source Operation

    fun loadProfile(
        id: Int? = null
    ): Flow<ApiEvent<PegawaiProfile?>> =
        flow {
            runCatching {

                val user = localDataSourceUser.selectCurrentUser()?.toDomain()
                val userId = requireNotNull(id ?: user?.idUser)

                val apiId = MasterPegawaiService.GetBy
                val apiResult = apiExecutor.callApi(apiId) {
                    apiService.getBy(id ?: userId)
                }

                val apiEvent: ApiEvent<PegawaiProfile?> = when (apiResult) {
                    is ApiResult.OnFailed -> apiResult.exception.toFailedEventNullable<PegawaiProfile>()
                    is ApiResult.OnSuccess -> with(apiResult.response) {
                        if (status == ApiException.FailedResponse.STATUS_FAIL) {
                            with(ApiException.FailedResponse(message)) {
                                apiExecutor.logException(apiId, this)
                                toFailedEventNullable<PegawaiProfile>()
                            }
                        } else toDomain()?.run {
                            localDataSourcePegawaiProfile.inserts(toEntity())
                            ApiEvent.OnSuccess.fromServer<PegawaiProfile?>(this)
                        } ?: with(ApiException.NullResponse) {
                            apiExecutor.logException(apiId, this)
                            toFailedEventNullable<PegawaiProfile>()
                        }
                    }
                }

                emit(apiEvent)
            }.onFailure {
                emit(ApiException.Unknown.toFailedEventNullable<PegawaiProfile>())
            }
        }

    //endregion

}
