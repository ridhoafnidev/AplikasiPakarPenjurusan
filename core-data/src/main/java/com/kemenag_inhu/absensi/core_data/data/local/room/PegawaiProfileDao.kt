package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import com.kemenag_inhu.absensi.core_data.data.local.entity.PegawaiProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PegawaiProfileDao : BaseDao<PegawaiProfileEntity>() {

    @Query("DELETE FROM PegawaiProfileEntity WHERE userId IS :id")
    abstract suspend fun deleteById(id: Int): Int

    //region Query with Coroutines

    @Query("SELECT * FROM PegawaiProfileEntity WHERE userId IS :id")
    abstract suspend fun selectById(id: Int): PegawaiProfileEntity?

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM PegawaiProfileEntity WHERE userId IS :id")
    abstract fun selectByIdAsFlow(id: Int): Flow<PegawaiProfileEntity?>

    //endregion

}
