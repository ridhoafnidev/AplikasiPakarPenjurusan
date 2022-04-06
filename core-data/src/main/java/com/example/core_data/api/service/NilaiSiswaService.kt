package com.example.core_data.api.service

import com.example.core_data.api.request.nilai_siswa.AddNilaiSiswaRequest
import com.example.core_data.api.response.nilai_siswa.NilaiSiswaGetAllResponse
import com.example.core_data.api.response.nilai_siswa.NilaiSiswaResponse
import retrofit2.http.*

interface NilaiSiswaService {

    @GET(GetNilaiSiswaAll)
    suspend fun getNilaiSiswaAll(): NilaiSiswaGetAllResponse

    @DELETE(DeleteNilaiSiswa)
    suspend fun deleteNilaiSiswaById(
        @Path("id_user") idUser: Int,
    ): Unit

    @POST(AddNilaiSiswa)
    suspend fun addNilaiSiswa(
        @Body body: AddNilaiSiswaRequest
    ): NilaiSiswaResponse

    companion object {
        //region API Path
        const val GetNilaiSiswaAll = "nilai-siswa"
        const val AddNilaiSiswa = "nilai-siswa"
        const val DeleteNilaiSiswa = "nilai-siswa/{id_user}"
        //endregion
    }
}