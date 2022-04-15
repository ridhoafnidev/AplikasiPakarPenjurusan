package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterStatusAbsensiEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterStatusAbsensiDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterStatusAbsensi(private val dao: MasterStatusAbsensiDao) {
    suspend fun getAll(): MasterStatusAbsensiEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterStatusAbsensiEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterStatusAbsensiEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}