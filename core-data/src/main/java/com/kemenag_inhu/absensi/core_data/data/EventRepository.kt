package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSource
import com.kemenag_inhu.absensi.core_data.data.local.entity.toDomain
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.domain.Event
import com.kemenag_inhu.absensi.core_data.domain.ListEvents
import com.kemenag_inhu.absensi.core_domain.repository.IEventDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val localDataSource: LocalDataSource
) : IEventDbRepository {
    override fun getEvents(): Flow<ListEvents> = localDataSource.getEvents().map { it.toDomain() }

    override suspend fun insertEvent(event: Event) = localDataSource.insertEvent(event.toEntity())
}