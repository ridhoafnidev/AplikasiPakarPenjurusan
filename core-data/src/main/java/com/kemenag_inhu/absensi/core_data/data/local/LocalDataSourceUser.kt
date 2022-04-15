package com.kemenag_inhu.absensi.core_data.data.local

import com.kemenag_inhu.absensi.core_data.data.local.entity.UserEntity
import com.kemenag_inhu.absensi.core_data.data.local.entity.toEntity
import com.kemenag_inhu.absensi.core_data.data.local.room.UserDao
import com.kemenag_inhu.absensi.core_domain.model.User
import kotlinx.coroutines.flow.Flow

class LocalDataSourceUser(private val dao: UserDao) {
    suspend fun selectCurrentUser(): UserEntity? = dao.selectCurrentUser()
    fun selectCurrentUserAsFlow(): Flow<UserEntity?> = dao.selectCurrentUserAsFlow()
    suspend fun setCurrentUser(user: User?) = dao.setCurrentUser(user?.toEntity())
}