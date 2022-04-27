package com.example.core_data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core_data.domain.ListSiswa
import com.example.core_data.domain.Siswa

@Entity
data class SiswaEntity(
    @PrimaryKey
    val idUser: Long,
    val id: Long?,
    val username: String,
    val level: String,
    val lastLogin: String,
    val createdAt: String,
    val updatedAt: String,
    val nisn: String,
    val nama: String,
    val kelas: String,
    val tanggalLahir: String,
    val agama: String,
    val alamat: String,
    val foto: String,
    val asalSekolah: String,
    val statusAsalSekolah: String,
    val namaAyah: String,
    val umurAyah: String,
    val agamaAyah: String,
    val pendidikanTerakhirAyah: String,
    val pekerjaanAyah: String,
    val namaIbu: String,
    val umurIbu: String,
    val agamaIbu: String,
    val pendidikanTerakhirIbu: String,
    val pekerjaanIbu: String,
    val tempatLahir: String
)

internal typealias SiswaEntities = List<SiswaEntity>

internal fun SiswaEntity.toDomain() =
    Siswa(
        idUser = idUser,
        id = id,
        username = username,
        level = level,
        lastLogin = lastLogin,
        createdAt = createdAt,
        updatedAt = updatedAt,
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

internal fun SiswaEntities.toDomain() =
    map { it.toDomain() }

internal fun Siswa.toEntity() =
    SiswaEntity(
        idUser = idUser,
        id = id,
        username = username,
        level = level,
        lastLogin = lastLogin,
        createdAt = createdAt,
        updatedAt = updatedAt,
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

internal fun ListSiswa.toEntity() =
    map { it.toEntity() }