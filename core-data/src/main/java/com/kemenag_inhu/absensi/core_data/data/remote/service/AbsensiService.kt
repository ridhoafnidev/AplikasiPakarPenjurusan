package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.AbsensiResponse
import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterJabatanStrukturalResponse
import com.kemenag_inhu.absensi.core_data.data.remote.response.PegawaiProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AbsensiService {

    @GET(GetAll)
    suspend fun getAll(): AbsensiResponse

    @GET(GetBy)
    suspend fun getBy(
        @Path("id") id: Int
    ): AbsensiResponse

    companion object {
        //region API Path

        const val GetAll = "get-absensi-all"
        const val GetBy = "get-absensi-by/{id}"

        //endregion
    }

}