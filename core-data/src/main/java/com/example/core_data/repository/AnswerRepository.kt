package com.example.core_data.repository

import com.example.core_data.api.*
import com.example.core_data.api.ApiExecutor
import com.example.core_data.api.ApiResult
import com.example.core_data.api.request.RequestAnswer
import com.example.core_data.api.request.RequestAnswerInsert
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.api.service.AnswerService
import com.example.core_data.api.service.UserService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.Answers
import com.example.core_data.domain.User
import com.example.core_data.persistence.dao.UserDao
import com.example.core_data.persistence.entity.toDomain
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AnswerRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val answerService: AnswerService,
    private val dao: UserDao,
    private val jsonParser: Moshi,
) {

    fun getCurrentUserAsFlow() = dao.selectAuthAsFlow().map { auth ->
        ApiEvent.OnSuccess.fromCache(auth?.toDomain())
    }

    suspend fun getAuth() = dao.selectAuth()
        ?.toDomain()

    suspend fun saveAnswer(
        siswaId: Long,
        result: String,
        answers: Answers
    ) : Flow<ApiEvent<CommonResponse>> = flow {
        runCatching {
            val apiId = AnswerService.Answer

            val saveAnswers = RequestAnswerInsert(siswaId, result, RequestAnswer.fromAnswers(answers))

            val apiResult = apiExecutor.callApi(apiId) {
                answerService.answerRequest(saveAnswers)
            }

            val apiEvent: ApiEvent<CommonResponse> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response) {
                    when {
                        message.equals(ApiException.FailedResponse.STATUS_FAILED, true) -> {
                            ApiException.FailedResponse(message).let {
                                it.toFailedEvent()
                            }
                        }
                        else -> ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<CommonResponse>())
        }
    }

}