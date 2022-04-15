package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.ListAbsensi
import com.kemenag_inhu.absensi.core_domain.model.ListDataAbsensi

@Entity
data class ListDataAbsensiEntity(
    @PrimaryKey
    val userId: String = "0",
    val absensi: ListAbsensi = emptyList(),
    val hadir: Int = 0,
    val cuti: Int = 0,
    val sakit: Int = 0,
    val alfa: Int = 0
)

//region Converter form Entity to Domain

internal fun ListDataAbsensiEntity.toDomain() =
    ListDataAbsensi(
        userId = userId,
        absensi = absensi,
        hadir = hadir,
        cuti = cuti,
        sakit = sakit,
        alfa = alfa
    )

//endregion
//region Converter from Domain to Entity

internal fun ListDataAbsensi.toEntity() =
    ListDataAbsensiEntity(
        userId = userId,
        absensi = absensi,
        hadir = hadir,
        cuti = cuti,
        sakit = sakit,
        alfa = alfa
    )

//endregion

