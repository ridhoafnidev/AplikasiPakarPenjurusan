package com.kemenag_inhu.absensi.core_domain.model

data class MasterStatusAbsensi(
    val idStatusAbsensi: Int,
    val statusAbsensi: String
)

typealias ListMasterStatusAbsensi = List<MasterStatusAbsensi>






