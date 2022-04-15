package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJabatanStrukturalEntities
import com.kemenag_inhu.absensi.core_data.data.local.entity.MasterJabatanStrukturalEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterJabatanStrukturalDao : BaseDao<MasterJabatanStrukturalEntity>() {

    @Transaction
    open suspend fun replaceAll(entities: MasterJabatanStrukturalEntities) {
        deleteAll()
        insertAll(entities)
    }

    //region Query with Coroutines

    @Query("SELECT * FROM MasterJabatanStrukturalEntity ORDER BY idJabatanStruktural ASC")
    abstract suspend fun selectAll(): MasterJabatanStrukturalEntities

    @Query("DELETE FROM MasterJabatanStrukturalEntity")
    abstract suspend fun deleteAll(): Int

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM MasterJabatanStrukturalEntity ORDER BY idJabatanStruktural ASC")
    abstract fun selectAllAsFlow(): Flow<MasterJabatanStrukturalEntities>

    //endregion

}
