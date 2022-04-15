package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.PegawaiEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.PegawaiEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterPegawaiDao : BaseDao<PegawaiEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: PegawaiEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM PegawaiEntity ORDER BY idPegawai ASC")
    abstract suspend fun selectAll(): PegawaiEntities

    @Query("DELETE FROM PegawaiEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM PegawaiEntity ORDER BY idPegawai ASC")
    abstract fun selectAllAsFlow(): Flow<PegawaiEntities>

    //endregion

}
