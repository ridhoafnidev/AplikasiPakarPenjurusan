package com.kemenag_inhu.absensi.core_domain.repository

import com.kemenag_inhu.absensi.core_data.domain.Event
import com.kemenag_inhu.absensi.core_data.domain.ListEvents
import kotlinx.coroutines.flow.Flow

interface IEventDbRepository {
    fun getEvents(): Flow<ListEvents>
    suspend fun insertEvent(event: Event)
}