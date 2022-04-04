package com.example.core_data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core_data.domain.LastResult
import com.example.core_data.domain.LastResults

@Entity
data class LastResultEntity(
    @PrimaryKey
    val idHasil: Int = 0,
    val siswaId: Int = 0,
    val hasilAkhir: String = "",
    val nama: String = "",
    )

typealias LastResultEntities = List<LastResultEntity>

//region Convert from Entity to Domain

internal fun LastResultEntity.toDomain() =
    LastResult(
       idHasil = idHasil,
       siswaId = siswaId,
       hasilAkhir = hasilAkhir,
       nama = nama
    )

internal fun LastResultEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun LastResult.toEntity() =
    LastResultEntity(idHasil = idHasil, siswaId = siswaId, hasilAkhir = hasilAkhir, nama = nama)

internal fun LastResults.toEntity() =
    map { it.toEntity() }

//endregion