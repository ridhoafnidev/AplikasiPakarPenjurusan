package com.example.core_data.api.service

import com.example.core_data.api.request.siswa.UpdateSiswaRequest
import com.example.core_data.api.response.siswa.SiswaResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface SiswaService {

    @Multipart
    @POST(UpdateFoto)
    suspend fun updatePhotoSiswa(
        @Path("id_user") idUser: Int,
        @Part foto: MultipartBody.Part,
    ): SiswaResponse

    @PUT(UpdateSiswa)
    suspend fun updateSiswa(
        @Path("id_user") idUser: Int,
        @Body body: UpdateSiswaRequest
    ): SiswaResponse

    companion object {
        //region API Path
        const val UpdateFoto = "update-foto/{id_user}"
        const val UpdateSiswa = "update-siswa/{id_user}"
        //endregion
    }
}