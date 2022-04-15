package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterLevelEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterLevelDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterLevel(private val dao: MasterLevelDao) {
    suspend fun getAll(): MasterLevelEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterLevelEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterLevelEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}