package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterJenisTenaga
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterJenisTenagaResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterJenisTenagaResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterJenisTenagaResponse(
    @Json(name = "id_master_jenis_tenaga")
    val idMasterJenisTenaga: Int = 0,
    @Json(name = "jenis_tenaga")
    val jenisTenaga: String = "",
)

//region Convert from Response to Domain

internal fun MasterJenisTenagaResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterJenisTenagaResponse.toDomain() =
    MasterJenisTenaga(idMasterJenisTenaga = idMasterJenisTenaga, jenisTenaga = jenisTenaga)

//endregion
