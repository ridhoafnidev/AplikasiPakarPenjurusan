package com.example.core_data.api.response.nilai_siswa

import com.example.core_data.domain.NilaiSiswa
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NilaiSiswaGetAllResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: ListNilaiSiswaResponse
)

@JsonClass(generateAdapter = true)
data class NilaiSiswaResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: NilaiSiswaResponseData
)

@JsonClass(generateAdapter = true)
data class NilaiSiswaResponseData(
    @Json(name = "id")
    val id: Long = 0L,
    @Json(name = "user_id")
    val user_id: Long = 0L,
    @Json(name = "rata_raport_ipa")
    val rata_raport_ipa: Long = 0L,
    @Json(name = "rata_raport_ips")
    val rata_raport_ips: Long = 0L,
    @Json(name = "rata_akhir")
    val rata_akhir: Long = 0L,
)

typealias ListNilaiSiswaResponse = List<NilaiSiswaResponseData>

fun ListNilaiSiswaResponse.toDomain() = map {
    it.toDomain()
}

internal fun NilaiSiswaResponseData.toDomain() =
    NilaiSiswa(
        id = id,
        user_id = user_id,
        rata_raport_ipa = rata_raport_ipa,
        rata_raport_ips = rata_raport_ips,
        rata_akhir = rata_akhir,
    )