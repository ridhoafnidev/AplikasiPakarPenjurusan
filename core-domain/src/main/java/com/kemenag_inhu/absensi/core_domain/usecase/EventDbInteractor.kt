package com.kemenag_inhu.absensi.core_domain.usecase

import com.kemenag_inhu.absensi.core_data.domain.Event
import com.kemenag_inhu.absensi.core_data.domain.ListEvents
import com.kemenag_inhu.absensi.core_domain.repository.IEventDbRepository
import kotlinx.coroutines.flow.Flow

class EventDbInteractor(private val eventDbRepository: IEventDbRepository) :
    EventUseCase {
    override fun getEvents(): Flow<ListEvents> = eventDbRepository.getEvents()
    override suspend fun insertEvent(event: Event) = eventDbRepository.insertEvent(event)
}