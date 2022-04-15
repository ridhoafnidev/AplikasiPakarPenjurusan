package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterJenisTenagaResponse
import retrofit2.http.GET

interface MasterJenisTenagaService {

    @GET(GetAll)
    suspend fun getAll(): MasterJenisTenagaResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-jenis-tenaga-all"

        //endregion
    }

}