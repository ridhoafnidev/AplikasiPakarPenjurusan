package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterStatusAbsensi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterStatusAbsensiResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterStatusAbsensiResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterStatusAbsensiResponse(
    @Json(name = "id_status_absensi")
    val idStatusAbsensi: Int = 0,
    @Json(name = "status_absensi")
    val statusAbsensi: String = ""
)

//region Convert from Res
// ponse to Domain

internal fun MasterStatusAbsensiResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterStatusAbsensiResponse.toDomain() =
    MasterStatusAbsensi(idStatusAbsensi = idStatusAbsensi, statusAbsensi = statusAbsensi)

//endregion
