package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterPnsNonpnsEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterPnsNonpnsEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterPnsNonpnsDao : BaseDao<MasterPnsNonpnsEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterPnsNonpnsEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterPnsNonpnsEntity ORDER BY idMasterPnsNonpns ASC")
    abstract suspend fun selectAll(): MasterPnsNonpnsEntities

    @Query("DELETE FROM MasterPnsNonpnsEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterPnsNonpnsEntity ORDER BY idMasterPnsNonpns ASC")
    abstract fun selectAllAsFlow(): Flow<MasterPnsNonpnsEntities>

    //endregion

}
