package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterPangkatGolongan
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterPangkatGolonganResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterPangkatGolonganResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterPangkatGolonganResponse(
    @Json(name = "id_pangkat_golongan")
    val idPangkatGolongan: Int = 0,
    @Json(name = "pangkat_golongan")
    val pangkatGolongan: String = ""
)

//region Convert from Response to Domain

internal fun MasterPangkatGolonganResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterPangkatGolonganResponse.toDomain() =
    MasterPangkatGolongan(idPangkatGolongan = idPangkatGolongan, pangkatGolongan = pangkatGolongan)

//endregion
