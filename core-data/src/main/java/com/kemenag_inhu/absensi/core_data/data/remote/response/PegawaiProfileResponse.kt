package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.PegawaiProfile
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PegawaiProfileResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: DataPegawaiProfileResponse? = null
)

internal fun PegawaiProfileResponse.toDomain() = data?.toDomain()

@JsonClass(generateAdapter = true)
data class DataPegawaiProfileResponse(
    @Json(name = "id_pegawai")
    val idPegawai: Int = 0,
    @Json(name = "user_id")
    val userId: Int = 0,
    @Json(name = "nik")
    val nik: String = "",
    @Json(name = "nip")
    val nip: String = "",
    @Json(name = "nama_lengkap")
    val namaLengkap: String = "",
    @Json(name = "email")
    val email: String = "",
    @Json(name = "no_hp")
    val noHp: String = "",
    @Json(name = "pns_nonpns_id")
    val pnsNonpnsId: Int = 0,
    @Json(name = "jenis_tenaga_id")
    val jenisTenagaId: Int = 0,
    @Json(name = "unit_kerja_id")
    val unitKerjaId: Int = 0,
    @Json(name = "jabatan_struktural_id")
    val jabatanStrukturalId: Int = 0,
    @Json(name = "jabatan_fungsional_id")
    val jabatanFungsionalId: Int = 0,
    @Json(name = "pangkat_golongan_id")
    val pangkatGolonganId: Int = 0,
    @Json(name = "pns_nonpns")
    val pnsNonpns: String = "",
    @Json(name = "jenis_tenaga")
    val jenisTenaga: String = "",
    @Json(name = "unit_kerja")
    val unitKerja: String = "",
    @Json(name = "jabatan_struktural")
    val jabatanStruktural: String = "",
    @Json(name = "jabatan_fungsional")
    val jabatanFungsional: String = "",
    @Json(name = "pangkat_golongan")
    val pangkatGolongan: String = "",
    @Json(name = "is_active")
    val isActive: Int = 0,
)

//region Convert from Res
// ponse to Domain

internal fun DataPegawaiProfileResponse.toDomain() =
    PegawaiProfile(
        idPegawai = idPegawai,
        userId = userId,
        nik = nik,
        nip = nip,
        namaLengkap = namaLengkap,
        email = email,
        noHp = noHp,
        pnsNonpnsid = pnsNonpnsId,
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
        isActive = isActive
    )

//endregion
