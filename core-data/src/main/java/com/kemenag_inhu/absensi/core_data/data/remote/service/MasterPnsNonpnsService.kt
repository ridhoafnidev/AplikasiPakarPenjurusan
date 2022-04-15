package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterPnsNonpnsResponse
import retrofit2.http.GET

interface MasterPnsNonpnsService {

    @GET(GetAll)
    suspend fun getAll(): MasterPnsNonpnsResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-pns-nonpns-all"

        //endregion
    }

}