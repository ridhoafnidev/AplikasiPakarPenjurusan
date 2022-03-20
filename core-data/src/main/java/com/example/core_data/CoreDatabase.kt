package com.example.core_data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_data.persistence.dao.UserDao
import com.example.core_data.persistence.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = BuildConfig.schemaDatabaseVersion,
)

internal abstract class CoreDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}