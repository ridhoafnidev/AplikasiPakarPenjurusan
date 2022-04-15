package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJabatanStrukturalEntities
import com.kemenag_inhu.absensi.core_data.data.local.room.MasterJabatanStrukturalDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceMasterJabatanStruktural(private val dao: MasterJabatanStrukturalDao) {
    suspend fun getAll(): MasterJabatanStrukturalEntities = dao.selectAll()
    fun getAllAsFlow(): Flow<MasterJabatanStrukturalEntities> = dao.selectAllAsFlow()
    suspend fun replaceAll(items: MasterJabatanStrukturalEntities) = dao.replaceAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}