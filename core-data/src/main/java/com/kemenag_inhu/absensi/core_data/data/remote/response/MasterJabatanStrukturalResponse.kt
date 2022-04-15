package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanFungsional
import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanStruktural
import com.kemenag_inhu.absensi.core_domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterJabatanStrukturalResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterJabatanStrukturalResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterJabatanStrukturalResponse(
    @Json(name = "id_master_jabatan_struktural")
    val idJabatanStruktural: Int = 0,
    @Json(name = "jabatan_struktural")
    val jabatanStruktural: String = "",
)

//region Convert from Response to Domain

internal fun MasterJabatanStrukturalResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterJabatanStrukturalResponse.toDomain() =
    MasterJabatanStruktural(idMasterJabatanStruktural = idJabatanStruktural, jabatanStruktural = jabatanStruktural)

//endregion
