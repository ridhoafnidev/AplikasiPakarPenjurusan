package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kemenag_inhu.absensi.core_data.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao : BaseDao<UserEntity>() {

    @Transaction
    open suspend fun setCurrentUser(user: UserEntity?) {
        selectCurrentUser()?.let {
            updates(it.copy(isCurrent = false))
        }
        user?.let {
            inserts(it.copy(isCurrent = true))
        }
    }

    //region Query with Coroutines

    @Query("SELECT * FROM UserEntity WHERE isCurrent")
    abstract suspend fun selectCurrentUser(): UserEntity?

    //endregion
    //region Query with Flow

    @Query("SELECT * FROM UserEntity WHERE isCurrent")
    abstract fun selectCurrentUserAsFlow(): Flow<UserEntity?>

    //endregion

}
