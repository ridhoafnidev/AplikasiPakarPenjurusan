package com.example.core_data.api.service

import com.example.core_data.api.request.RequestAnswerInsert
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.api.response.HasilDataResponse
import com.example.core_data.api.response.HasilResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnswerService {

    @POST(Answer)
    suspend fun answerRequest(
        @Body request: RequestAnswerInsert
    ) : CommonResponse

    @GET(Result)
    suspend fun getHasilBySiswaId(
        @Path("siswa_id") siswaId: Int,
        @Path("is_teacher") isTeacher: Int
    ) : HasilResponse

    companion object {
        //region API Path

        const val Answer = "hasil-jawaban-insert"
        const val Result = "hasil-by-siswa-id/{siswa_id}/{is_teacher}"

        //endregion
    }
}