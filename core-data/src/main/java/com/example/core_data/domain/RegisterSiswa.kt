package com.example.core_data.domain

data class RegisterSiswa(
    val idUser: Long = 0L,
    val userId: Long = 0L,
    val username: String = "",
    val nisn: String = "",
    val nama: String = "",
    val kelas: String = "",
    val tempatLahir: String = "",
    val tanggalLahir: String = "",
    val agama: String = "",
    val alamat: String = "",
    val asalSekolah: String = "",
    val statusAsalSekolah: String = "",
    val namaAyah: String = "",
    val umurAyah: String = "",
    val agamaAyah: String = "",
    val pendidikanTerakhirAyah: String = "",
    val pekerjaanAyah: String = "",
    val namaIbu: String = "",
    val umurIbu: String = "",
    val agamaIbu: String = "",
    val pendidikanTerakhirIbu: String = "",
    val pekerjaanIbu: String = ""
)