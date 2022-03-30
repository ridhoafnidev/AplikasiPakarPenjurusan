package com.example.core_data.api.request.siswa

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateSiswaRequest(
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "nisn")
    val nisn: String = "",
    @Json(name = "kelas")
    val kelas: String = "",
    @Json(name = "tanggal_lahir")
    val tanggalLahir: String = "",
    @Json(name = "agama")
    val agama: String = "",
    @Json(name = "alamat")
    val alamat: String = "",
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
    @Json(name = "agama_ibu")
    val agamaIbu: String = "",
    @Json(name = "pendidikan_terakhir_ibu")
    val pendidikanTerakhirIbu: String = "",
    @Json(name = "pekerjaan_ibu")
    val pekerjaanIbu: String = "",
    @Json(name = "tempat_lahir")
    val tempatLahir: String = ""
)