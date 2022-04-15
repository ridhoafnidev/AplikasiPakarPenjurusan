package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class MasterUnitKerjaEntity(
    @PrimaryKey
    val idMasterUnitKerja: Int,
    val unitKerja: String
    )

typealias MasterUnitKerjaEntities = List<MasterUnitKerjaEntity>

//region Convert from Entity to Domain

internal fun MasterUnitKerjaEntity.toDomain() =
    MasterUnitKerja(idMasterUnitKerja = idMasterUnitKerja, unitKerja = unitKerja)


internal fun MasterUnitKerjaEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterUnitKerja.toEntity() =
    MasterUnitKerjaEntity(idMasterUnitKerja = idMasterUnitKerja, unitKerja = unitKerja)

internal fun ListMasterUnitKerja.toEntity() =
    map { it.toEntity() }

//endregion