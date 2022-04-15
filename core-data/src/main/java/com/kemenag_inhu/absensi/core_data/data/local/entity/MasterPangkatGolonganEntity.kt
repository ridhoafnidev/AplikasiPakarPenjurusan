package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class MasterPangkatGolonganEntity(
    @PrimaryKey
    val idPangkatGolongan: Int,
    val pangkatGolongan: String,
    )

typealias MasterPangkatGolonganEntities = List<MasterPangkatGolonganEntity>

//region Convert from Entity to Domain

internal fun MasterPangkatGolonganEntity.toDomain() =
    MasterPangkatGolongan(idPangkatGolongan = idPangkatGolongan, pangkatGolongan = pangkatGolongan)


internal fun MasterPangkatGolonganEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterPangkatGolongan.toEntity() =
    MasterPangkatGolonganEntity(idPangkatGolongan = idPangkatGolongan, pangkatGolongan = pangkatGolongan)

internal fun ListMasterPangkatGolongan.toEntity() =
    map { it.toEntity() }

//endregion