package com.example.core_data.persistence.dao

import androidx.room.*
import com.example.core_data.persistence.entity.SiswaEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SiswaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun inserts(vararg item: SiswaEntity): List<Long>

    @Query("SELECT * FROM SiswaEntity")
    abstract suspend fun selectSiswaAll(): SiswaEntity?

    @Query("SELECT * FROM SiswaEntity WHERE idUser = :idUser")
    abstract suspend fun selectSiswaById(idUser: Int): SiswaEntity?

    @Query("SELECT * FROM SiswaEntity")
    abstract fun selectSiswaAllAsFlow(): Flow<SiswaEntity?>

    @Query("SELECT * FROM SiswaEntity WHERE idUser = :idUser")
    abstract fun selectSiswaByIdAsFlow(idUser: Int): Flow<SiswaEntity?>

    @Query("DELETE FROM SiswaEntity")
    abstract suspend fun deleteAll(): Int

    @Query("DELETE FROM SiswaEntity WHERE idUser = :idUser")
    abstract suspend fun deleteById(idUser: Int): Int

    @Transaction
    open suspend fun replace(entity: SiswaEntity) {
        deleteAll()
        inserts(entity)
    }

    @Transaction
    open suspend fun replaceById(idUser: Int, entity: SiswaEntity) {
        deleteById(idUser)
        inserts(entity)
    }
}