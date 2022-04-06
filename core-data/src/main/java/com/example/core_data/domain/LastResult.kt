package com.example.core_data.domain

data class LastResult(
    val idHasil: Int = 0,
    val siswaId: Int = 0,
    val userId: Int = 0,
    val hasilAkhir: String = "",
    val nama: String = ""
)

typealias LastResults = List<LastResult>

fun LastResult.isIpa() = hasilAkhir == IPA
fun LastResult.isIps() = hasilAkhir == IPS
fun LastResult.isIpc() = hasilAkhir == IPC

const val IPA = "IPA"
const val IPS = "IPS"
const val IPC = "IPC"