package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import com.kemenag_inhu.absensi.core_data.data.local.entity.ListDataAbsensiEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ListDataAbsensiDao : BaseDao<ListDataAbsensiEntity>() {

    @Query("DELETE FROM ListDataAbsensiEntity WHERE userId IS :id")
    abstract suspend fun deleteById(id: Int): Int

    //region Query with Coroutines

    @Query("SELECT * FROM ListDataAbsensiEntity WHERE userId IS :id")
    abstract suspend fun selectById(id: Int): ListDataAbsensiEntity?

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM ListDataAbsensiEntity WHERE userId IS :id")
    abstract fun selectByIdAsFlow(id: Int): Flow<ListDataAbsensiEntity?>

    //endregion

}
