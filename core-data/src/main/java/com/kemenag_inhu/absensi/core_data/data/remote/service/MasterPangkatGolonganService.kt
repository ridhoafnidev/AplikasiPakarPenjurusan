package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterPangkatGolonganResponse
import retrofit2.http.GET

interface MasterPangkatGolonganService {

    @GET(GetAll)
    suspend fun getAll(): MasterPangkatGolonganResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-pangkat-golongan-all"

        //endregion
    }

}