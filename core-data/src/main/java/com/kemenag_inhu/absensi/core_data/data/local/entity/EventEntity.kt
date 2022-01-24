package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_data.domain.Event

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val location: String = "",
    val startDate: String = "",
    val startTime: String = "",
    val endDate: String = "",
    val endTime: String = "",
    val image: String = "",
    val description: String = "",
    val poweredBy: String = "",
)

typealias ListEventsEntity = List<EventEntity>

fun ListEventsEntity.toDomain() = map {
    it.toDomain()
}

fun EventEntity.toDomain() =
    Event(
        id = id, name = name, location = location, startDate = startDate, startTime = startTime,
        endDate = endDate, endTime = endTime, image = image, description = description,
        poweredBy = poweredBy
    )

fun Event.toEntity() =
    EventEntity( name = name, location = location, startDate = startDate, startTime = startTime,
        endDate = endDate, endTime = endTime, image = image, description = description,
        poweredBy = poweredBy
    )
