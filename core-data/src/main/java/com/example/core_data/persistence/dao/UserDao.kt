package com.example.core_data.persistence.dao

import androidx.room.*
import com.example.core_data.persistence.BaseDao
import com.example.core_data.persistence.entity.UserEntities
import com.example.core_data.persistence.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun inserts(vararg item: UserEntity): List<Long>

    @Query("SELECT * FROM UserEntity")
    abstract suspend fun selectAuth(): UserEntity?

    @Query("SELECT * FROM UserEntity")
    abstract fun selectAuthAsFlow(): Flow<UserEntity?>

    @Query("DELETE FROM UserEntity")
    abstract suspend fun deleteAll(): Int

    @Transaction
    open suspend fun replace(entity: UserEntity) {
        deleteAll()
        inserts(entity)
    }
}