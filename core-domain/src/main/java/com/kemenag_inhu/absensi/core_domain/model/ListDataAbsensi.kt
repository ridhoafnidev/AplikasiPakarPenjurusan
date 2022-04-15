package com.kemenag_inhu.absensi.core_domain.model

data class ListDataAbsensi(
    val userId: Int = 0,
    val absensi: ListAbsensi = emptyList(),
    val hadir: Int = 0,
    val cuti: Int = 0,
    val sakit: Int = 0,
    val alfa: Int = 0
)
