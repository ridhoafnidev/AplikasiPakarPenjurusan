package com.example.core_data.repository

import com.example.core_data.api.*
import com.example.core_data.api.ApiExecutor
import com.example.core_data.api.ApiResult
import com.example.core_data.api.service.AnswerService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.LastResult
import com.example.core_data.persistence.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LastResultRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val answerService: AnswerService,
    private val dao: UserDao,
) {

    fun getLastResult(
        siswaId: Long
    ) : Flow<ApiEvent<LastResult>> = flow {
        runCatching {
            val apiId = AnswerService.Result

            val apiResult = apiExecutor.callApi(apiId) {
                answerService.getHasilBySiswaId(siswaId)
            }

            val apiEvent: ApiEvent<LastResult> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.result) {
                    when {
                        else -> ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<LastResult>())
        }
    }
}