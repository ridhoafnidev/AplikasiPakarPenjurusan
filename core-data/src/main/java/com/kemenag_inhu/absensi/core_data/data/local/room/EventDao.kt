package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kemenag_inhu.absensi.core_data.data.local.entity.EventEntity
import com.kemenag_inhu.absensi.core_data.data.local.entity.ListEventsEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertEvent(event: EventEntity)

    @Query("SELECT * FROM EventEntity")
    abstract fun getEventsAsFlow(): Flow<ListEventsEntity>

}