package com.kemenag_inhu.absensi.core_domain.model

data class PegawaiProfile(
    val idPegawai: Int,
    val userId: Int,
    val nik: String,
    val nip: String,
    val namaLengkap: String,
    val email: String,
    val noHp: String,
    val pnsNonpnsid: Int,
    val jenisTenagaId: Int,
    val unitKerjaId: Int,
    val jabatanStrukturalId: Int,
    val jabatanFungsionalId: Int,
    val pangkatGolonganId: Int,
    val pnsNonpns: String,
    val jenisTenaga: String,
    val unitKerja: String,
    val jabatanStruktural: String,
    val jabatanFungsional: String,
    val pangkatGolongan: String,
    val isActive: Int,
)






