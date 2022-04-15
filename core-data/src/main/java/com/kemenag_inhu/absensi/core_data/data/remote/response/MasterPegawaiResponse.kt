package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.Pegawai
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterPegawaiResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterPegawaiResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterPegawaiResponse(
    @Json(name = "id_pegawai")
    val idPegawai: Int = 0,
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
    @Json(name = "is_active")
    val isActive: Int = 0,
)

//region Convert from Res
// ponse to Domain

internal fun MasterPegawaiResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterPegawaiResponse.toDomain() =
    Pegawai(
        idPegawai = idPegawai,
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
        isActive = isActive
    )

//endregion
