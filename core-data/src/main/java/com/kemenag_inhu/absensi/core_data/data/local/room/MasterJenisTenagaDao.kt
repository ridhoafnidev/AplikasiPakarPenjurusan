package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJenisTenagaEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJenisTenagaEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterJenisTenagaDao : BaseDao<MasterJenisTenagaEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterJenisTenagaEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterJenisTenagaEntity ORDER BY idJenisTenaga ASC")
    abstract suspend fun selectAll(): MasterJenisTenagaEntities

    @Query("DELETE FROM MasterJenisTenagaEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterJenisTenagaEntity ORDER BY idJenisTenaga ASC")
    abstract fun selectAllAsFlow(): Flow<MasterJenisTenagaEntities>

    //endregion

}
