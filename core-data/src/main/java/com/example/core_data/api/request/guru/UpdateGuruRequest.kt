package com.example.core_data.api.request.guru

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateGuruRequest(
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "nip")
    val nip: String = "",
    @Json(name = "alamat")
    val alamat: String = "",
    @Json(name = "email")
    val email: String = ""
)