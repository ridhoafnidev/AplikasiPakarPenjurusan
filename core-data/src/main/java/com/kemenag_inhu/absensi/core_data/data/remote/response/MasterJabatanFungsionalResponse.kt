package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanFungsional
import com.kemenag_inhu.absensi.core_domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterJabatanFungsionalResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterJabatanFungsionalResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterJabatanFungsionalResponse(
    @Json(name = "id_jabatan_fungsional")
    val idJabatanFungsional: Int = 0,
    @Json(name = "jabatan_fungsional")
    val jabatanFungsional: String = "",
)

//region Convert from Response to Domain

internal fun MasterJabatanFungsionalResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterJabatanFungsionalResponse.toDomain() =
    MasterJabatanFungsional(idJabatanFungsional = idJabatanFungsional, jabatanFungsional = jabatanFungsional)

//endregion
