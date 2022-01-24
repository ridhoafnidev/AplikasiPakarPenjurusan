package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.EventEntity
import com.kemenag_inhu.absensi.core_data.data.local.entity.ListEventsEntity
import com.kemenag_inhu.absensi.core_data.data.local.room.EventDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val eventDao: EventDao) {
    fun getEvents(): Flow<ListEventsEntity> = eventDao.getEventsAsFlow()
    suspend fun insertEvent(event: EventEntity) = eventDao.insertEvent(event)
}