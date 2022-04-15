package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterUnitKerjaEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterUnitKerjaEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterUnitKerjaDao : BaseDao<MasterUnitKerjaEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterUnitKerjaEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterUnitKerjaEntity ORDER BY idMasterUnitKerja ASC")
    abstract suspend fun selectAll(): MasterUnitKerjaEntities

    @Query("DELETE FROM MasterUnitKerjaEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterUnitKerjaEntity ORDER BY idMasterUnitKerja ASC")
    abstract fun selectAllAsFlow(): Flow<MasterUnitKerjaEntities>

    //endregion

}
