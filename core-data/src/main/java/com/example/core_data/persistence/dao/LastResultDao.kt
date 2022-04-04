package com.example.core_data.persistence.dao

import androidx.room.*
import com.example.core_data.persistence.entity.LastResultEntities
import com.example.core_data.persistence.entity.LastResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LastResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun inserts(items: LastResultEntities): List<Long>

    @Query("SELECT * FROM LastResultEntity")
    abstract suspend fun selectLastResult(): LastResultEntity?

    @Query("SELECT * FROM LastResultEntity")
    abstract fun selectLastResultAsFlow(): Flow<LastResultEntity?>

    @Query("DELETE FROM LastResultEntity")
    abstract suspend fun deleteAll(): Int

    @Transaction
    open suspend fun replace(entities: LastResultEntities) {
        deleteAll()
        inserts(entities)
    }
}