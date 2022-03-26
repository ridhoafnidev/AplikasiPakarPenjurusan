package com.example.core_data.persistence.dao

import androidx.room.*
import com.example.core_data.persistence.entity.GuruEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GuruDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun inserts(vararg item: GuruEntity): List<Long>

    @Query("SELECT * FROM GuruEntity")
    abstract suspend fun selectGuruAll(): GuruEntity?

    @Query("SELECT * FROM GuruEntity WHERE idUser = :idUser")
    abstract suspend fun selectGuruById(idUser: Int): GuruEntity?

    @Query("SELECT * FROM GuruEntity")
    abstract fun selectGuruAllAsFlow(): Flow<GuruEntity?>

    @Query("SELECT * FROM GuruEntity WHERE idUser = :idUser")
    abstract fun selectGuruByIdAsFlow(idUser: Int): Flow<GuruEntity?>

    @Query("DELETE FROM GuruEntity")
    abstract suspend fun deleteAll(): Int

    @Query("DELETE FROM GuruEntity WHERE idUser = :idUser")
    abstract suspend fun deleteById(idUser: Int): Int

    @Transaction
    open suspend fun replace(entity: GuruEntity) {
        deleteAll()
        inserts(entity)
    }

    @Transaction
    open suspend fun replaceById(idUser: Int, entity: GuruEntity) {
        deleteById(idUser)
        inserts(entity)
    }
}