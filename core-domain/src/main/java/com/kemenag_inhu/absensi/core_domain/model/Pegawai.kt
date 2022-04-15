package com.kemenag_inhu.absensi.core_domain.model

data class Pegawai(
    val idPegawai: Int,
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
    val isActive: Int,
)

typealias ListPegawai = List<Pegawai>






