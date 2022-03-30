package com.example.core_data.api.service

import com.example.core_data.api.request.guru.UpdateGuruRequest
import com.example.core_data.api.response.guru.GuruResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface GuruService {

    @Multipart
    @POST(UpdateFoto)
    suspend fun updatePhotoGuru(
        @Path("id_user") idUser: Int,
        @Part foto: MultipartBody.Part,
    ): GuruResponse

    @PUT(UpdateGuru)
    suspend fun updateGuru(
        @Path("id_user") idUser: Int,
        @Body body: UpdateGuruRequest
    ): GuruResponse

    companion object {
        //region API Path
        const val UpdateFoto = "update-foto/{id_user}"
        const val UpdateGuru = "update-guru/{id_user}"
        //endregion
    }
}