package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class PegawaiEntity(
    @PrimaryKey
    val idPegawai: Int,
    val nik: String,
    val nip: String,
    val namaLengkap: String,
    val email: String,
    val noHp: String,
    val pnsNonpnsid: Int,
    val jenisTenagaId: Int,
    val unitKerjaId: Int,
    val jabatanStrukturalId: Int,
    val jabatanFungsionalId: Int,
    val pangkatGolonganId: Int,
    val isActive: Int,
    )

typealias PegawaiEntities = List<PegawaiEntity>

//region Convert from Entity to Domain

internal fun PegawaiEntity.toDomain() =
    Pegawai(
        idPegawai = idPegawai,
        nik = nik,
        nip = nip,
        namaLengkap = namaLengkap,
        email = email,
        noHp = noHp,
        pnsNonpnsid = pnsNonpnsid,
        jenisTenagaId = jenisTenagaId,
        unitKerjaId = unitKerjaId,
        jabatanStrukturalId = jabatanStrukturalId,
        jabatanFungsionalId = jabatanFungsionalId,
        pangkatGolonganId = pangkatGolonganId,
        isActive = isActive)


internal fun PegawaiEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun Pegawai.toEntity() =
    PegawaiEntity(idPegawai = idPegawai,
        nik = nik,
        nip = nip,
        namaLengkap = namaLengkap,
        email = email,
        noHp = noHp,
        pnsNonpnsid = pnsNonpnsid,
        jenisTenagaId = jenisTenagaId,
        unitKerjaId = unitKerjaId,
        jabatanStrukturalId = jabatanStrukturalId,
        jabatanFungsionalId = jabatanFungsionalId,
        pangkatGolonganId = pangkatGolonganId,
        isActive = isActive)

internal fun ListPegawai.toEntity() =
    map { it.toEntity() }

//endregion