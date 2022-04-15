package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.ListDataAbsensiEntity
import com.kemenag_inhu.absensi.core_data.data.local.room.ListDataAbsensiDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceAbsensi(private val dao: ListDataAbsensiDao) {
    suspend fun selectById(id: Int): ListDataAbsensiEntity? = dao.selectById(id)
    fun selectByIdAsFlow(id: Int): Flow<ListDataAbsensiEntity?> = dao.selectByIdAsFlow(id)
    suspend fun deleteBy(id: Int) = dao.deleteById(id)
    suspend fun inserts(data: ListDataAbsensiEntity) = dao.inserts(data)
}