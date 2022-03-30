package com.example.core_data.api.response.guru

import com.example.core_data.domain.RegisterGuru
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterGuruResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: RegisterGuruDataResponse
)

@JsonClass(generateAdapter = true)
data class RegisterGuruDataResponse(
    @Json(name = "id")
    val idUser: Long = 0L,
    @Json(name = "user_id")
    val userId: Long = 0L,
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "username")
    val username: String = "",
    @Json(name = "nip")
    val nip: String = "",
    @Json(name = "alamat")
    val alamat: String = "",
    @Json(name = "foto")
    val foto: String = "",
    @Json(name = "email")
    val email: String = "",
    @Json(name = "updated_at")
    val updatedAt: String? = "",
    @Json(name = "created_at")
    val createdAt: String = "",
)

internal fun RegisterGuruDataResponse.toDomain() =
    RegisterGuru(
        idUser = idUser,
        userId = userId,
        nama = nama,
        username = username,
        nip = nip,
        alamat = alamat,
        foto = foto,
        email = email,
        updatedAt = updatedAt ?: "",
        createdAt = createdAt,
    )