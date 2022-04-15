package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterPnsNonpnsEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterPnsNonpnsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterPnsNonpns(private val dao: MasterPnsNonpnsDao) {
    suspend fun getAll(): MasterPnsNonpnsEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterPnsNonpnsEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterPnsNonpnsEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}