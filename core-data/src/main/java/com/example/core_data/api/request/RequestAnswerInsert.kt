package com.example.core_data.api.request

import com.example.core_data.domain.Answers
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestAnswerInsert(
    @Json(name = "siswa_id")
    val siswa_id: Long,
    @Json(name = "hasil_akhir")
    val hasilAkhir: String,
    @Json(name = "jawaban_id")
    val jenisKerusakan: List<RequestAnswer>
)

@JsonClass(generateAdapter = true)
data class RequestAnswer(
    @Json(name = "jawaban_id")
    val jawaban_id: String,
) {
    companion object {
        fun fromAnswers(answers: Answers): List<RequestAnswer> =
            answers.map {
                RequestAnswer(it.answer)
            }
    }

}