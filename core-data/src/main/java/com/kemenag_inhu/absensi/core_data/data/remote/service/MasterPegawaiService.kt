package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterPegawaiResponse
import com.kemenag_inhu.absensi.core_data.data.remote.response.PegawaiProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MasterPegawaiService {

    @GET(GetAll)
    suspend fun getAll(): MasterPegawaiResponse

    @GET(GetBy)
    suspend fun getBy(
        @Path("id") idPegawai: Int
    ): PegawaiProfileResponse

    companion object {
        //region API Path

        const val GetAll = "get-pegawai-all"
        const val GetBy = "get-pegawai-by/{id}"

        //endregion
    }

}