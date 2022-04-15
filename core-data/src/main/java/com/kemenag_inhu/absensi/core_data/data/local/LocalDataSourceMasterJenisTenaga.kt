package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJenisTenagaEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterJenisTenagaDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterJenisTenaga(private val dao: MasterJenisTenagaDao) {
    suspend fun getAll(): MasterJenisTenagaEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterJenisTenagaEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterJenisTenagaEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}