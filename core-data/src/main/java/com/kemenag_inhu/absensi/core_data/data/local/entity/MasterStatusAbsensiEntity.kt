package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class MasterStatusAbsensiEntity(
    @PrimaryKey
    val idStatusAbsensi: Int,
    val statusAbsensi: String
    )

typealias MasterStatusAbsensiEntities = List<MasterStatusAbsensiEntity>

//region Convert from Entity to Domain

internal fun MasterStatusAbsensiEntity.toDomain() =
    MasterStatusAbsensi(idStatusAbsensi = idStatusAbsensi, statusAbsensi = statusAbsensi)


internal fun MasterStatusAbsensiEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterStatusAbsensi.toEntity() =
    MasterStatusAbsensiEntity(idStatusAbsensi = idStatusAbsensi, statusAbsensi = statusAbsensi)

internal fun ListMasterStatusAbsensi.toEntity() =
    map { it.toEntity() }

//endregion