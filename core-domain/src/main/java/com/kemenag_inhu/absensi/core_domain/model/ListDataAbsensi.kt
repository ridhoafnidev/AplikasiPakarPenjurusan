package com.kemenag_inhu.absensi.core_domain.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import androidx.room.TypeConverter

data class ListDataAbsensi(
    val userId: String = "0",
    val absensi: ListAbsensi = emptyList(),
    val hadir: Int = 0,
    val cuti: Int = 0,
    val sakit: Int = 0,
    val alfa: Int = 0
)

object AbsensiConverter {

    private val absensiAdapter by lazy {
        Moshi.Builder().build().adapter(Absensi::class.java)
    }

    private val listAbsensiAdapter by lazy<JsonAdapter<ListAbsensi>> {
        Moshi.Builder().build().adapter(
            Types.newParameterizedType(List::class.java, Absensi::class.java)
        )
    }

    @TypeConverter
    fun fromAbsensi(absensi: Absensi?): String? =
        absensi?.let { absensiAdapter.toJson(it) }

    @TypeConverter
    fun fromAbsensis(listAbsensi: ListAbsensi): String? =
        listAbsensiAdapter.toJson(listAbsensi)

    @TypeConverter
    fun toAbsensi(absensi: String?): Absensi? =
        absensi?.let { absensiAdapter.fromJson(it) }

    @TypeConverter
    fun fromAbsensis(absensi: String?): ListAbsensi =
        absensi?.let { listAbsensiAdapter.fromJson(absensi) } ?: emptyList()

}
