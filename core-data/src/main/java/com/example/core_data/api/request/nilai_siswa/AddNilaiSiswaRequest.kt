package com.example.core_data.api.request.nilai_siswa

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddNilaiSiswaRequest(
    @Json(name = "id_user")
    val idUser: String = "",
    @Json(name = "rata_raport_ipa")
    val rataRaportIpa: String = "",
    @Json(name = "rata_raport_ips")
    val rataRaportIps: String = ""
)