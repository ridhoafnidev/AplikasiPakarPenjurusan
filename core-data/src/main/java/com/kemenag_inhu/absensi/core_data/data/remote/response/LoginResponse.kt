package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<LoginDataResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class LoginDataResponse(
    @Json(name = "id_user")
    val idUser: Int = 0,
    @Json(name = "nik")
    val nik: Int = 0,
    @Json(name = "nip")
    val nip: Int = 0,
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "level_id")
    val levelId: Int,
    @Json(name = "is_active")
    val isActive: Int,
    @Json(name = "email")
    val email: String = "",
    @Json(name = "no_hp")
    val noHp: String = ""
)

//region Convert from Response to Domain

internal fun LoginResponse.toDomain() =
    data.map { it.toDomain() }

internal fun LoginDataResponse.toDomain() =
    User(idUser = idUser, nik = nik, nip = nip, levelId = levelId, isActive = isActive, nama = nama, email = email, noHp = noHp)


//endregion
