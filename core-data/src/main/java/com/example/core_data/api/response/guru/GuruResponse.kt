package com.example.core_data.api.response.guru

import com.example.core_data.domain.Guru
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GuruResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: GuruDataResponse
)

@JsonClass(generateAdapter = true)
data class GuruDataResponse(
    @Json(name = "id_user")
    val idUser: Long = 0L,
    @Json(name = "username")
    val username: String = "",
    @Json(name = "level")
    val level: String = "",
    @Json(name = "last_login")
    val lastLogin: String = "",
    @Json(name = "created_at")
    val createdAt: String = "",
    @Json(name = "updated_at")
    val updatedAt: String? = "",
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "nip")
    val nip: String = "",
    @Json(name = "alamat")
    val alamat: String = "",
    @Json(name = "foto")
    val foto: String = "",
    @Json(name = "email")
    val email: String = ""
)

typealias ListGuruResponse = List<GuruDataResponse>

fun ListGuruResponse.toDomain() = map {
    it.toDomain()
}

internal fun GuruDataResponse.toDomain() =
    Guru(
        idUser = idUser,
        username = username,
        level = level,
        lastLogin = lastLogin,
        createdAt = createdAt,
        updatedAt = updatedAt ?: "",
        nama = nama,
        nip = nip,
        alamat = alamat,
        foto = foto,
        email = email
    )