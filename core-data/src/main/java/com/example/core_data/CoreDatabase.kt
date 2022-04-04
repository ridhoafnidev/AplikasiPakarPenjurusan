package com.example.core_data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_data.persistence.dao.GuruDao
import com.example.core_data.persistence.dao.LastResultDao
import com.example.core_data.persistence.dao.SiswaDao
import com.example.core_data.persistence.dao.UserDao
import com.example.core_data.persistence.entity.LastResultEntity
import com.example.core_data.persistence.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        LastResultEntity::class
    ],
    version = BuildConfig.schemaDatabaseVersion,
)

internal abstract class CoreDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun lastResultDao(): LastResultDao
    abstract fun siswaDao(): SiswaDao
    abstract fun guruDao(): GuruDao
}