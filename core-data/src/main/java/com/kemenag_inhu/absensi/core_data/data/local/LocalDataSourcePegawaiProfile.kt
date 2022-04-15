package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.PegawaiProfileEntity
import com.kemenag_inhu.absensi.core_data.data.local.room.PegawaiProfileDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourcePegawaiProfile(private val dao: PegawaiProfileDao) {
    suspend fun selectById(id: Int): PegawaiProfileEntity? = dao.selectById(id)
    fun selectByIdAsFlow(id: Int): Flow<PegawaiProfileEntity?> = dao.selectByIdAsFlow(id)
    suspend fun deleteBy(id: Int) = dao.deleteById(id)
    suspend fun inserts(pegawaiNurse: PegawaiProfileEntity) = dao.inserts(pegawaiNurse)
}