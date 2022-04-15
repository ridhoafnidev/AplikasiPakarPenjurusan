package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterJenisTenaga
import com.kemenag_inhu.absensi.core_domain.model.MasterLevel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterLevelResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterLevelResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterLevelResponse(
    @Json(name = "id_level")
    val idLevel: Int = 0,
    @Json(name = "level")
    val level: String = "",
    @Json(name = "is_active")
    val isActive: Int = 0,
)

//region Convert from Response to Domain

internal fun MasterLevelResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterLevelResponse.toDomain() =
    MasterLevel(idLevel = idLevel, level = level, isActive = isActive)

//endregion
