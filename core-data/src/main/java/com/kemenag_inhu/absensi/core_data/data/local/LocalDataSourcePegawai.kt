package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.PegawaiEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterPegawaiDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourcePegawai(private val dao: MasterPegawaiDao) {
    suspend fun getAll(): PegawaiEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<PegawaiEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: PegawaiEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}