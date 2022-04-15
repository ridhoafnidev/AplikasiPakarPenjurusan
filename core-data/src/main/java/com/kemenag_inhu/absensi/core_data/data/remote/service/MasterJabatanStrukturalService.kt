package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterJabatanStrukturalResponse
import retrofit2.http.GET

interface MasterJabatanStrukturalService {

    @GET(GetAll)
    suspend fun getAll(): MasterJabatanStrukturalResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-jabatan-struktural-all"

        //endregion
    }

}