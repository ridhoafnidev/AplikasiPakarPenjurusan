package com.example.core_data.repository

import com.example.core_data.api.ApiEvent
import com.example.core_data.api.ApiExecutor
import com.example.core_data.api.ApiResult
import com.example.core_data.api.response.toDomain
import com.example.core_data.api.service.AuthService
import com.example.core_data.api.service.UserService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.User
import com.example.core_data.domain.Users
import com.example.core_data.persistence.dao.UserDao
import com.example.core_data.persistence.entity.toDomain
import com.example.core_data.persistence.entity.toEntity
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AuthRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val authService: UserService,
    private val dao: UserDao,
    private val jsonParser: Moshi,
) {

    fun getCurrentUserAsFlow() = dao.selectAuthAsFlow().map { auth ->
        ApiEvent.OnSuccess.fromCache(auth?.toDomain())
    }

    suspend fun getAuth() = dao.selectAuth()
        ?.toDomain()

    fun login(
        email: String,
        password: String
    ): Flow<ApiEvent<User>> = flow {
        runCatching {
            val apiId = UserService.Login

            val apiResult = apiExecutor.callApi(apiId) {
                authService.login(email, password)
            }

            val apiEvent: ApiEvent<User> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()

                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    dao.replace(
                        toDomain().toEntity().copy(isCurrent = true)
                    )
                    ApiEvent.OnSuccess.fromServer(this.toDomain())
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<User>())
        }
    }

}