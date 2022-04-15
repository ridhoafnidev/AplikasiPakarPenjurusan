package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJabatanFungsionalEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJabatanFungsionalEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterJabatanFungsionalDao : BaseDao<MasterJabatanFungsionalEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterJabatanFungsionalEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterJabatanFungsionalEntity ORDER BY idJabatanFungsional ASC")
    abstract suspend fun selectAll(): MasterJabatanFungsionalEntities

    @Query("DELETE FROM MasterJabatanFungsionalEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterJabatanFungsionalEntity ORDER BY idJabatanFungsional ASC")
    abstract fun selectAllAsFlow(): Flow<MasterJabatanFungsionalEntities>

    //endregion

}
