package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterPangkatGolonganEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterPangkatGolonganEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterPangkatGolonganDao : BaseDao<MasterPangkatGolonganEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterPangkatGolonganEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterPangkatGolonganEntity ORDER BY idPangkatGolongan ASC")
    abstract suspend fun selectAll(): MasterPangkatGolonganEntities

    @Query("DELETE FROM MasterPangkatGolonganEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterPangkatGolonganEntity ORDER BY idPangkatGolongan ASC")
    abstract fun selectAllAsFlow(): Flow<MasterPangkatGolonganEntities>

    //endregion

}
