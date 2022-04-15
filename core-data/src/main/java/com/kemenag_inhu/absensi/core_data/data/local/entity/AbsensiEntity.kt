package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class AbsensiEntity(
    @PrimaryKey
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

typealias AbsensiEntities = List<AbsensiEntity>

//region Convert from Entity to Domain

internal fun AbsensiEntity.toDomain() =
    Absensi(
        idAbsensi = idAbsensi,
        timestampAbsensi = timestampAbsensi,
        statusAbsensiId = statusAbsensiId,
        tanggalMulai = tanggalMulai,
        tanggalSelesai = tanggalSelesai,
        dokumenPendukung = dokumenPendukung,
        jenisCuti = jenisCuti,
        lembur = lembur,
        keterangan = keterangan,
        lat = lat,
        lng = lng,
        createdAt = createdAt,
        updatedAt = updatedAt,
        userId = userId,
        jenisAbsensi = jenisAbsensi
    )


internal fun AbsensiEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun Absensi.toEntity() =
    AbsensiEntity(
        idAbsensi = idAbsensi,
        timestampAbsensi = timestampAbsensi,
        statusAbsensiId = statusAbsensiId,
        tanggalMulai = tanggalMulai,
        tanggalSelesai = tanggalSelesai,
        dokumenPendukung = dokumenPendukung,
        jenisCuti = jenisCuti,
        lembur = lembur,
        keterangan = keterangan,
        lat = lat,
        lng = lng,
        createdAt = createdAt,
        updatedAt = updatedAt,
        userId = userId,
        jenisAbsensi = jenisAbsensi
    )

internal fun ListAbsensi.toEntity() =
    map { it.toEntity() }

//endregion