package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.ListMasterJabatanStruktural
import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanFungsional
import com.kemenag_inhu.absensi.core_domain.model.MasterJabatanStruktural

@Entity
data class MasterJabatanStrukturalEntity(
    @PrimaryKey
    val idJabatanStruktural: Int,
    val jabatanStruktural: String,
    )

typealias MasterJabatanStrukturalEntities = List<MasterJabatanStrukturalEntity>

//region Convert from Entity to Domain

internal fun MasterJabatanStrukturalEntity.toDomain() =
    MasterJabatanStruktural(idMasterJabatanStruktural = idJabatanStruktural, jabatanStruktural = jabatanStruktural)


internal fun MasterJabatanStrukturalEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun MasterJabatanStruktural.toEntity() =
    MasterJabatanStrukturalEntity(idJabatanStruktural = idMasterJabatanStruktural, jabatanStruktural = jabatanStruktural)

internal fun ListMasterJabatanStruktural.toEntity() =
    map { it.toEntity() }

//endregion