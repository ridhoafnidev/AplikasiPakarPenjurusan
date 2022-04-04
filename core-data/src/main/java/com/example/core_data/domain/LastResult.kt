package com.example.core_data.domain

data class LastResult(
    val idHasil: Int,
    val siswaId: Int,
    val hasilAkhir: String,
    val nama: String
)

typealias LastResults = List<LastResult>