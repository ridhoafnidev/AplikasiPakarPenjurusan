package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterUnitKerjaResponse
import retrofit2.http.GET

interface MasterUnitKerjaService {

    @GET(GetAll)
    suspend fun getAll(): MasterUnitKerjaResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-unit-kerja-all"

        //endregion
    }

}