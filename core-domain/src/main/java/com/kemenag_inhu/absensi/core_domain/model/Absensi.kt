package com.kemenag_inhu.absensi.core_domain.model

data class Absensi(
    val idAbsensi: Int,
    val timestampAbsensi: String,
    val statusAbsensiId: Int,
    val tanggalMulai: String,
    val tanggalSelesai: String,
    val dokumenPendukung: String,
    val jenisCuti: String,
    val lembur: Int,
    val keterangan: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val createdAt: String = "",
    val updatedAt: String = "",
    val userId: Int = 0,
    val jenisAbsensi: String = "",
)

typealias ListAbsensi = List<Absensi>





