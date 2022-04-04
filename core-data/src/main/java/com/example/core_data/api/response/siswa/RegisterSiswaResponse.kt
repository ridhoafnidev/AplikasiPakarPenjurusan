package com.example.core_data.api.response.siswa

import com.example.core_data.api.response.guru.RegisterGuruDataResponse
import com.example.core_data.domain.RegisterGuru
import com.example.core_data.domain.RegisterSiswa
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterSiswaResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: RegisterSiswaDataResponse
)

@JsonClass(generateAdapter = true)
data class RegisterSiswaDataResponse(
    @Json(name = "id")
    val idUser: Long = 0L,
    @Json(name = "user_id")
    val userId: Long = 0L,
    @Json(name = "username")
    val username: String = "",
    @Json(name = "nisn")
    val nisn: String = "",
    @Json(name = "nama")
    val nama: String = "",
    @Json(name = "kelas")
    val kelas: String = "",
    @Json(name = "tempat_lahir")
    val tempatLahir: String = "",
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
    val pekerjaanIbu: String = ""
)

internal fun RegisterSiswaDataResponse.toDomain() =
    RegisterSiswa(
        idUser = idUser,
        userId = userId,
        username = username,
        nisn = nisn,
        nama = nama,
        kelas = kelas,
        tempatLahir = tempatLahir,
        tanggalLahir = tanggalLahir,
        agama = agama,
        alamat = alamat,
        asalSekolah = asalSekolah,
        statusAsalSekolah = statusAsalSekolah,
        namaAyah = namaAyah,
        umurAyah = umurAyah,
        agamaAyah = agamaAyah,
        pendidikanTerakhirAyah = pendidikanTerakhirAyah,
        pekerjaanAyah = pekerjaanAyah,
        namaIbu = namaIbu,
        umurIbu = umurIbu,
        agamaIbu = agamaIbu,
        pendidikanTerakhirIbu = pendidikanTerakhirIbu,
        pekerjaanIbu = pekerjaanIbu
    )

