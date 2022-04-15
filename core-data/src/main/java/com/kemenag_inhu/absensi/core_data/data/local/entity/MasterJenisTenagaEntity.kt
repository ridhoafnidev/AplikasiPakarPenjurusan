package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class MasterJenisTenagaEntity(
    @PrimaryKey
    val idJenisTenaga: Int,
    val jenisTenaga: String,
    )

typealias MasterJenisTenagaEntities = List<MasterJenisTenagaEntity>

//region Convert from Entity to Domain

internal fun MasterJenisTenagaEntity.toDomain() =
    MasterJenisTenaga(idMasterJenisTenaga = idJenisTenaga, jenisTenaga = jenisTenaga)


internal fun MasterJenisTenagaEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterJenisTenaga.toEntity() =
    MasterJenisTenagaEntity(idJenisTenaga = idMasterJenisTenaga, jenisTenaga = jenisTenaga)

internal fun ListMasterJenisTenaga.toEntity() =
    map { it.toEntity() }

//endregion