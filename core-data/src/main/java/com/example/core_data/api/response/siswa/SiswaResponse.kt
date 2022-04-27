package com.example.core_data.api.response.siswa

import com.example.core_data.api.response.guru.GuruDataResponse
import com.example.core_data.api.response.guru.toDomain
import com.example.core_data.domain.Siswa
import com.example.core_data.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SiswaResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: SiswaDataResponse
)

@JsonClass(generateAdapter = true)
data class SiswaGetAllResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: ListSiswaResponse
)

@JsonClass(generateAdapter = true)
data class SiswaDataResponse(
    @Json(name = "id_user")
    val idUser: Long?,
    @Json(name = "user_id")
    val userId: Long?,
    @Json(name = "id")
    val id: Long?,
    @Json(name = "username")
    val username: String = "",
    @Json(name = "level")
    val level: String = "",
    @Json(name = "last_login")
    val lastLogin: String = "",
    @Json(name = "created_at")
    val createdAt: String = "",
    @Json(name = "updated_at")
    val updatedAt: String? = "",
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
    @Json(name = "agama_ibu")
    val agamaIbu: String = "",
    @Json(name = "pendidikan_terakhir_ibu")
    val pendidikanTerakhirIbu: String = "",
    @Json(name = "pekerjaan_ibu")
    val pekerjaanIbu: String = "",
    @Json(name = "tempat_lahir")
    val tempatLahir: String = ""
)

typealias ListSiswaResponse = List<SiswaDataResponse>

fun ListSiswaResponse.toDomain() = map {
    it.toDomain()
}

//region Convert from Response to Domain

internal fun SiswaDataResponse.toDomain() =
    Siswa(
        idUser = idUser ?: (userId ?: 0L),
        id = id,
        username = username,
        level = level,
        lastLogin = lastLogin,
        createdAt = createdAt,
        updatedAt = updatedAt ?: "",
        nisn = nisn,
        nama = nama,
        kelas = kelas,
        tanggalLahir = tanggalLahir,
        agama = agama,
        alamat = alamat,
        foto = foto,
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
        pekerjaanIbu = pekerjaanIbu,
        tempatLahir = tempatLahir
    )
