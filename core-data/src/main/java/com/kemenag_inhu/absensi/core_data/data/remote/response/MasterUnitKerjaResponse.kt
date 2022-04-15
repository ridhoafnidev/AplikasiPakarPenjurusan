package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterUnitKerja
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterUnitKerjaResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterUnitKerjaResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterUnitKerjaResponse(
    @Json(name = "id_master_unit_kerja")
    val idMasterUnitKerja: Int = 0,
    @Json(name = "unit_kerja")
    val unitKerja: String = ""
)

//region Convert from Res
// ponse to Domain

internal fun MasterUnitKerjaResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterUnitKerjaResponse.toDomain() =
    MasterUnitKerja(idMasterUnitKerja = idMasterUnitKerja, unitKerja = unitKerja)

//endregion
