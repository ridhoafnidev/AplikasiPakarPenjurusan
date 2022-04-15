package com.kemenag_inhu.absensi.core_domain.model

data class MasterLevel(
    val idLevel: Int,
    val level: String,
    val isActive: Int,
)

typealias ListMasterLevel = List<MasterLevel>






