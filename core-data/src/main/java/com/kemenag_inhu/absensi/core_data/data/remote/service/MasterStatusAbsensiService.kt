package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterStatusAbsensiResponse
import retrofit2.http.GET

interface MasterStatusAbsensiService {

    @GET(GetAll)
    suspend fun getAll(): MasterStatusAbsensiResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-status-absensi-all"

        //endregion
    }

}