package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterLevelEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterLevelEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterLevelDao : BaseDao<MasterLevelEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterLevelEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterLevelEntity ORDER BY idLevel ASC")
    abstract suspend fun selectAll(): MasterLevelEntities

    @Query("DELETE FROM MasterLevelEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterLevelEntity ORDER BY idLevel ASC")
    abstract fun selectAllAsFlow(): Flow<MasterLevelEntities>

    //endregion

}
