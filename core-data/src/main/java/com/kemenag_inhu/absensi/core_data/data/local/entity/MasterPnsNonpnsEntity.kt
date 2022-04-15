package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class MasterPnsNonpnsEntity(
    @PrimaryKey
    val idMasterPnsNonpns: Int,
    val pnsNonpns: String
    )

typealias MasterPnsNonpnsEntities = List<MasterPnsNonpnsEntity>

//region Convert from Entity to Domain

internal fun MasterPnsNonpnsEntity.toDomain() =
    MasterPnsNonpns(idMasterPnsNonpns = idMasterPnsNonpns, pnsNonpns = pnsNonpns)


internal fun MasterPnsNonpnsEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterPnsNonpns.toEntity() =
    MasterPnsNonpnsEntity(idMasterPnsNonpns = idMasterPnsNonpns, pnsNonpns = pnsNonpns)

internal fun ListMasterPnsNonpns.toEntity() =
    map { it.toEntity() }

//endregion