package com.example.core_data.api.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestAnswerInsert(
    @Json(name = "siswa_id")
    val siswa_id: Long,
    @Json(name = "jawaban_id")
    val jenisKerusakan: List<RequestAnswer>
)

@JsonClass(generateAdapter = true)
data class RequestAnswer(
    @Json(name = "jawaban_id")
    val jawaban_id: Long,
)