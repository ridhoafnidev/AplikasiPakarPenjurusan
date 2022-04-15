package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.entity.toDomain
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.local.room.UserDao
import com.kemenag_inhu.absensi.core_data.data.remote.api.*
import com.kemenag_inhu.absensi.core_data.data.remote.response.toDomain
import com.kemenag_inhu.absensi.core_data.data.remote.service.UserService
import com.kemenag_inhu.absensi.core_domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val apiService: UserService,
    private val userDao: UserDao,
    ){

    //region Local Data Source Operation

    fun getCurrentUserAsFlow() = userDao.selectCurrentUserAsFlow().map {
        ApiEvent.OnSuccess.fromCache(it?.toDomain())
    }

    suspend fun setCurrentUser(user: User?) = userDao.setCurrentUser(user?.toEntity())

    //endregion

    //region Remote Data Source Operation

  @Suppress("ComplexMethod")
  fun login(username: String, password: String): Flow<ApiEvent<User?>> = flow {
    runCatching {
        emit(ApiEvent.OnLoading(null))

        val apiId = UserService.Login
        val apiResult = apiExecutor.callApi(apiId) {
            apiService.login(username, password)
        }

        val apiEvent: ApiEvent<User?> = when(apiResult) {
            is ApiResult.OnFailed -> {
                apiResult.exception.toFailedEventNullable()
            }
            is ApiResult.OnSuccess -> with(apiResult.response) {
                when {
                    status == ApiException.FailedResponse.STATUS_FAIL -> {
                        with(ApiException.FailedResponse(message)) {
                            toFailedEventNullable<User>()
                        }
                    }

                    data.isEmpty() -> with(ApiException.EmptyResponse) {
                        toFailedEventNullable<User>()
                    }
                    else -> toDomain().firstOrNull()?.run {
                        userDao.setCurrentUser(
                            toEntity().copy(
                                isCurrent = true
                            )
                        )

                        ApiEvent.OnSuccess.fromServer(this.copy(
                            loginRemark = "SUCCESS"
                        ))
                    } ?: run {
                        with(ApiException.FailedResponse(apiResult.response.message)) {
                            toFailedEventNullable<User>()
                        }
                    }
                } //when
            }
        }

        emit(apiEvent)

    }.onFailure {
        emit(ApiException.Unknown.toFailedEventNullable<User>())
    }
}


    //endregion

}
