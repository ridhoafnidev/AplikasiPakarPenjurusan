package com.example.core_data.persistence

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Suppress("UnnecessaryAbstractClass")
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun inserts(vararg item: T): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(vararg item: T): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateAll(items: List<T>): Int

    @Delete
    abstract suspend fun deletes(vararg items: T): Int

    @Delete
    abstract suspend fun deleteAll(items: List<T>): Int

}