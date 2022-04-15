package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJabatanFungsionalEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterJabatanFungsionalDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterJabatanFungsional(private val dao: MasterJabatanFungsionalDao) {
    suspend fun getAll(): MasterJabatanFungsionalEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterJabatanFungsionalEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterJabatanFungsionalEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}