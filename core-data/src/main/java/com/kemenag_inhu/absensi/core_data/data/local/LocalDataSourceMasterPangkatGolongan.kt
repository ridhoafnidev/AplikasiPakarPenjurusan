package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterPangkatGolonganEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterPangkatGolonganDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterPangkatGolongan(private val dao: MasterPangkatGolonganDao) {
    suspend fun getAll(): MasterPangkatGolonganEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterPangkatGolonganEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterPangkatGolonganEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}