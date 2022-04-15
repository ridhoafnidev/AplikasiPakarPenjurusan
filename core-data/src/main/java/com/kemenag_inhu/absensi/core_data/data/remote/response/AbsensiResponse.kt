package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.Absensi
import com.kemenag_inhu.absensi.core_domain.model.ListDataAbsensi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbsensiResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: DataListAbsensiResponse? = null
)

@JsonClass(generateAdapter = true)
data class DataListAbsensiResponse(
    @Json(name = "user_id")
    val userId: String = "0",
    @Json(name = "absensi")
    val absensi: ListDataAbsensiResponse = emptyList(),
    @Json(name = "hadir")
    val hadir: Int = 0,
    @Json(name = "sakit")
    val sakit: Int = 0,
    @Json(name = "cuti")
    val cuti: Int = 0,
    @Json(name = "alfa")
    val alfa: Int = 0,
)

typealias ListDataAbsensiResponse = List<DataAbsensiResponse>

@JsonClass(generateAdapter = true)
data class DataAbsensiResponse(
    @Json(name = "id_absensi")
    val idAbsensi: Int = 0,
//    @Json(name = "timestamp_absensi")
//    val timestampAbsensi: String = "",
//    @Json(name = "status_absensi_id")
//    val statusAbsensiId: Int = 0,
//    @Json(name = "tanggal_mulai")
//    val tanggalMulai: String = "",
//    @Json(name = "tanggal_selesai")
//    val tanggalSelesai: String = "",
//    @Json(name = "dokumen_pendukung")
//    val dokumenPendukung: String = "",
//    @Json(name = "jenis_cuti")
//    val jenisCuti: String = "",
//    @Json(name = "lembur")
//    val lembur: Int = 0,
//    @Json(name = "keterangan")
//    val keterangan: String = "",
//    @Json(name = "lat")
//    val lat: Double = 0.0,
//    @Json(name = "lng")
//    val lng: Double = 0.0,
//    @Json(name = "alamat_absensi")
//    val alamatAbsensi: String = "",
//    @Json(name = "created_at")
//    val createdAt: String = "",
//    @Json(name = "updated_at")
//    val updatedAt: String = "",
//    @Json(name = "user_id")
//    val userId: Int = 0,
//    @Json(name = "jenis_absensi")
//    val jenisAbsensi: String = "",
)

//region Convert from Response to Domain

internal fun AbsensiResponse.toDomain() =
    data?.toDomain()

internal fun DataListAbsensiResponse.toDomain() =
    ListDataAbsensi(
        userId = userId,
        absensi = absensi.toDomain(),
        hadir = hadir,
        cuti = cuti,
        sakit = sakit,
        alfa = alfa
    )

internal fun ListDataAbsensiResponse.toDomain() =
    map { it.toDomain() }

internal fun DataAbsensiResponse.toDomain() =
    Absensi(
        idAbsensi = idAbsensi,
//        timestampAbsensi = timestampAbsensi,
//        statusAbsensiId = statusAbsensiId,
//        tanggalMulai = tanggalMulai,
//        tanggalSelesai = tanggalSelesai,
//        dokumenPendukung = dokumenPendukung,
//        jenisCuti = jenisCuti,
//        lembur = lembur,
//        keterangan = keterangan,
//        lat = lat,
//        lng = lng,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
//        userId = userId,
//        jenisAbsensi = jenisAbsensi
    )

//endregion
