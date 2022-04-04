package com.example.core_data.domain

data class NilaiSiswa(
    val id: Long = 0L,
    val user_id: Long = 0L,
    val rata_raport_ipa: Long = 0L,
    val rata_raport_ips: Long = 0L,
    val rata_akhir: Long = 0L,
)

typealias ListNilaiSiswa = List<NilaiSiswa>