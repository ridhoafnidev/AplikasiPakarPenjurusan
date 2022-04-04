package com.example.core_data.api.service

import com.example.core_data.api.response.nilai_siswa.NilaiSiswaGetAllResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NilaiSiswaService {

    @GET(GetNilaiSiswaAll)
    suspend fun getNilaiSiswaAll(): NilaiSiswaGetAllResponse

    @DELETE(DeleteNilaiSiswa)
    suspend fun deleteNilaiSiswaById(
        @Path("id_user") idUser: Int,
    ): Unit

    companion object {
        //region API Path
        const val GetNilaiSiswaAll = "nilai-siswa"
        const val DeleteNilaiSiswa = "nilai-siswa/{id_user}"
        //endregion
    }
}