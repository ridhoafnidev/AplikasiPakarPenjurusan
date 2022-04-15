package com.kemenag_inhu.absensi.core_data.data.remote.service

import com.kemenag_inhu.absensi.core_data.data.remote.response.LoginResponse
import com.kemenag_inhu.absensi.core_data.data.remote.response.MasterJabatanFungsionalResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MasterJabatanFungsionalService {

    @GET(GetAll)
    suspend fun getAll(): MasterJabatanFungsionalResponse

    companion object {
        //region API Path

        const val GetAll = "get-master-jabatan-fungsional-all"

        //endregion
    }

}