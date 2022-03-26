package com.example.core_data.domain

import com.squareup.moshi.Json

data class Siswa(
    @Json(name = "id_user")
    val idUser: Long = 0L,
    @Json(name = "username")
    val username: String = "",
    @Json(name = "level")
    val level: String = "",
    @Json(name = "last_login")
    val lastLogin: String = "",
    @Json(name = "created_at")
    val createdAt: String = "",
    @Json(name = "updated_at")
    val updatedAt: String = "",
    @Json(name = "nisn")
    val nisn: String = "",
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "kelas")
    val kelas: String = "",
    @Json(name = "tanggal_lahir")
    val tanggalLahir: String = "",
    @Json(name = "agama")
    val agama: String = "",
    @Json(name = "alamat")
    val alamat: String = "",
    @Json(name = "foto")
    val foto: String = "",
    @Json(name = "asal_sekolah")
    val asalSekolah: String = "",
    @Json(name = "status_asal_sekolah")
    val statusAsalSekolah: String = "",
    @Json(name = "nama_ayah")
    val namaAyah: String = "",
    @Json(name = "umur_ayah")
    val umurAyah: String = "",
    @Json(name = "agama_ayah")
    val agamaAyah: String = "",
    @Json(name = "pendidikan_terakhir_ayah")
    val pendidikanTerakhirAyah: String = "",
    @Json(name = "pekerjaan_ayah")
    val pekerjaanAyah: String = "",
    @Json(name = "nama_ibu")
    val namaIbu: String = "",
    @Json(name = "umur_ibu")
    val umurIbu: String = "",
    @Json(name = "pendidikan_ibu")
    val pendidikanIbu: String = "",
    @Json(name = "tempat_lahir")
    val tempatLahir: String = ""
)

typealias ListSiswa = List<Siswa>