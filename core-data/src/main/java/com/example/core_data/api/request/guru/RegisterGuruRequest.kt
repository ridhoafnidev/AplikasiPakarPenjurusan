package com.example.core_data.api.request.guru

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterGuruRequest(
    @Json(name = "level")
    val level: String = "",
    @Json(name = "username")
    val username: String = "",
    @Json(name = "password")
    val password: String = "",
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "nip")
    val nip: String = "",
    @Json(name = "alamat")
    val alamat: String = "",
    @Json(name = "email")
    val email: String = ""
)