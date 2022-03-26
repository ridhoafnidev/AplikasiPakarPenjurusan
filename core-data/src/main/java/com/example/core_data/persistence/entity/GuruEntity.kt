package com.example.core_data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core_data.domain.Guru
import com.example.core_data.domain.ListGuru

@Entity
data class GuruEntity(
    @PrimaryKey
    val idUser: Long,
    val username: String,
    val level: String,
    val lastLogin: String,
    val createdAt: String,
    val updatedAt: String,
    val nama: String,
    val nip: String,
    val alamat: String,
    val foto: String,
    val email: String
)

internal typealias GuruEntities = List<GuruEntity>

internal fun GuruEntity.toDomain() =
    Guru(
        idUser = idUser,
        username = username,
        level = level,
        lastLogin = lastLogin,
        createdAt = createdAt,
        updatedAt = updatedAt,
        nama = nama,
        nip = nip,
        alamat = alamat,
        foto = foto,
        email = email
    )

internal fun GuruEntities.toDomain() =
    map { it.toDomain() }

internal fun Guru.toEntity() =
    GuruEntity(
        idUser = idUser,
        username = username,
        level = level,
        lastLogin = lastLogin,
        createdAt = createdAt,
        updatedAt = updatedAt,
        nama = nama,
        nip = nip,
        alamat = alamat,
        foto = foto,
        email = email
    )

internal fun ListGuru.toEntity() =
    map { it.toEntity() }