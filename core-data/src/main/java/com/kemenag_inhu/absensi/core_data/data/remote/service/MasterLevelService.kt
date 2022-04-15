package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterLevelResponse
import retrofit2.http.GET

interface MasterLevelService {

    @GET(GetAll)
    suspend fun getAll(): MasterLevelResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-level-all"

        //endregion
    }

}