package com.example.core_data.api.service

import com.example.core_data.api.request.RequestAnswerInsert
import com.example.core_data.api.response.RequestAnswerResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AnswerService {

    @POST(Answer)
    suspend fun answerRequest(
        @Body request: RequestAnswerInsert
    ) : RequestAnswerResponse

    companion object {
        //region API Path

        const val Answer = "hasil-jawaban-insert"

        //endregion
    }
}