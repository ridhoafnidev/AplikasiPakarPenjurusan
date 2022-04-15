package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.ListMasterJabatanFungsional
import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanFungsional

@Entity
data class MasterJabatanFungsionalEntity(
    @PrimaryKey
    val idJabatanFungsional: Int,
    val jabatanFungsional: String,
    )

typealias MasterJabatanFungsionalEntities = List<MasterJabatanFungsionalEntity>

//region Convert from Entity to Domain

internal fun MasterJabatanFungsionalEntity.toDomain() =
    MasterJabatanFungsional(idJabatanFungsional = idJabatanFungsional, jabatanFungsional = jabatanFungsional)


internal fun MasterJabatanFungsionalEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterJabatanFungsional.toEntity() =
    MasterJabatanFungsionalEntity(idJabatanFungsional = idJabatanFungsional, jabatanFungsional = jabatanFungsional)

internal fun ListMasterJabatanFungsional.toEntity() =
    map { it.toEntity() }

//endregion