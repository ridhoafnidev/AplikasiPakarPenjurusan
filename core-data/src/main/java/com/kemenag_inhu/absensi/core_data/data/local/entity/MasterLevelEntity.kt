package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class MasterLevelEntity(
    @PrimaryKey
    val idLevel: Int,
    val level: String,
    val isActive: Int
    )

typealias MasterLevelEntities = List<MasterLevelEntity>

//region Convert from Entity to Domain

internal fun MasterLevelEntity.toDomain() =
    MasterLevel(idLevel = idLevel, level = level, isActive = isActive)


internal fun MasterLevelEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterLevel.toEntity() =
    MasterLevelEntity(idLevel = idLevel, level = level, isActive = isActive)

internal fun ListMasterLevel.toEntity() =
    map { it.toEntity() }

//endregion