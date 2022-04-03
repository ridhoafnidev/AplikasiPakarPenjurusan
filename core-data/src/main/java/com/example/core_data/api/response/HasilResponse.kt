package com.example.core_data.api.response

import com.example.core_data.domain.LastResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class HasilResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "status")
    val status: String = "",
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val result: ListHasilResponse,
)

typealias ListHasilResponse = List<HasilDataResponse>

@JsonClass(generateAdapter = true)
class HasilDataResponse(
    @Json(name = "id_hasil")
    val idHasil: Int = 0,
    @Json(name = "siswa_id")
    val siswaId: Int = 0,
    @Json(name = "hasil_akhir")
    val hasilAkhir: String = "",
)

fun ListHasilResponse.toDomain() = map {
    it
}

fun HasilDataResponse.toDomain() = LastResult(
    idHasil = idHasil,
    siswaId = siswaId,
    hasilAkhir = hasilAkhir
)

