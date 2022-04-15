package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterStatusAbsensiEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterStatusAbsensiEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterStatusAbsensiDao : BaseDao<MasterStatusAbsensiEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterStatusAbsensiEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterStatusAbsensiEntity ORDER BY idStatusAbsensi ASC")
    abstract suspend fun selectAll(): MasterStatusAbsensiEntities

    @Query("DELETE FROM MasterStatusAbsensiEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterStatusAbsensiEntity ORDER BY idStatusAbsensi ASC")
    abstract fun selectAllAsFlow(): Flow<MasterStatusAbsensiEntities>

    //endregion

}
