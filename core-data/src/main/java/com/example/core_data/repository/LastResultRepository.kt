package com.example.core_data.repository

import com.example.core_data.api.*
import com.example.core_data.api.ApiExecutor
import com.example.core_data.api.ApiResult
import com.example.core_data.api.response.toDomain
import com.example.core_data.api.service.AnswerService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.LastResults
import com.example.core_data.persistence.dao.LastResultDao
import com.example.core_data.persistence.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LastResultRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val answerService: AnswerService,
    private val dao: LastResultDao,
) {

    suspend fun getLastResult(
        siswaId: Int,
        isTeacher: Int
    ) : Flow<ApiEvent<LastResults>> = flow {
        runCatching {
            val apiId = AnswerService.Result

            val apiResult = apiExecutor.callApi(apiId) {
                answerService.getHasilBySiswaId(siswaId, isTeacher)
            }

            val apiEvent: ApiEvent<LastResults> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.result) {
                    dao.replace(
                        toDomain().toEntity()
                    )
                    ApiEvent.OnSuccess.fromServer(this.toDomain())
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<LastResults>())
        }
    }
}