package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.*

@Entity
data class PegawaiProfileEntity(
    @PrimaryKey
    val idPegawai: Int,
    val userId: Int,
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
    val pnsNonpns: String,
    val jenisTenaga: String,
    val unitKerja: String,
    val jabatanStruktural: String,
    val jabatanFungsional: String,
    val pangkatGolongan: String,
    val isActive: Int,
    )

//region Convert from Entity to Domain

internal fun PegawaiProfileEntity.toDomain() =
    PegawaiProfile(
        idPegawai = idPegawai,
        userId = userId,
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
        pnsNonpns = pnsNonpns,
        jenisTenaga = jenisTenaga,
        unitKerja = unitKerja,
        jabatanStruktural = jabatanStruktural,
        jabatanFungsional = jabatanFungsional,
        pangkatGolongan = pangkatGolongan,
        isActive = isActive)

//endregion
//region Convert from Domain to Entity

internal fun PegawaiProfile.toEntity() =
    PegawaiProfileEntity(
        idPegawai = idPegawai,
        userId = userId,
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
        pnsNonpns = pnsNonpns,
        jenisTenaga = jenisTenaga,
        unitKerja = unitKerja,
        jabatanStruktural = jabatanStruktural,
        jabatanFungsional = jabatanFungsional,
        pangkatGolongan = pangkatGolongan,
        isActive = isActive)


//endregion