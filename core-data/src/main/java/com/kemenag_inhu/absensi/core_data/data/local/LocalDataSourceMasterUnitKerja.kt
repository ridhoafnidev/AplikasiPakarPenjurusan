package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterUnitKerjaEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterUnitKerjaDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterUnitKerja(private val dao: MasterUnitKerjaDao) {
    suspend fun getAll(): MasterUnitKerjaEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterUnitKerjaEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterUnitKerjaEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}