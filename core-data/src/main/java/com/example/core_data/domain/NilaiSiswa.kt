package com.example.core_data.domain

data class NilaiSiswa(
    val id: Long = 0L,
    val user_id: Long = 0L,
    val rata_raport_ipa: Long = 0L,
    val rata_raport_ips: Long = 0L,
    val rata_akhir: Long = 0L,
)

typealias ListNilaiSiswa = List<NilaiSiswa>

fun NilaiSiswa.isIpa() = rata_akhir >= 70
fun NilaiSiswa.isIps() = rata_akhir < 70
fun NilaiSiswa.isIpaOrIps() = if(rata_akhir >= 70) IPA else IPS
