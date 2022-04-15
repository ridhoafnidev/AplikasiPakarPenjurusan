package com.kemenag_inhu.absensi.core_data.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kemenag_inhu.absensi.core_data.BuildConfig
import com.kemenag_inhu.absensi.core_data.data.local.entity.*

@Database(
    entities = [
        EventEntity::class,
        MasterJabatanFungsionalEntity::class,
        MasterJabatanStrukturalEntity::class,
        MasterJenisTenagaEntity::class,
        MasterLevelEntity::class,
        MasterPangkatGolonganEntity::class,
        PegawaiEntity::class,
        MasterPnsNonpnsEntity::class,
        MasterStatusAbsensiEntity::class,
        MasterUnitKerjaEntity::class,
        UserEntity::class,
        PegawaiProfileEntity::class,
    ],
    version = BuildConfig.schemaDatabaseVersion,
)

internal abstract class CoreDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun masterJabatanFungsionalDao(): MasterJabatanFungsionalDao
    abstract fun masterJabatanStruktulDao(): MasterJabatanStrukturalDao
    abstract fun masterJenisTenagaDao(): MasterJenisTenagaDao
    abstract fun masterLevelDao(): MasterLevelDao
    abstract fun masterPangkatGolonganDao(): MasterPangkatGolonganDao
    abstract fun masterPnsNonpnsDao(): MasterPnsNonpnsDao
    abstract fun masterStatusAbsensiDao(): MasterStatusAbsensiDao
    abstract fun masterUnitKerjaDao(): MasterUnitKerjaDao
    abstract fun userDao(): UserDao
    abstract fun masterPegawaiDao(): MasterPegawaiDao
    abstract fun pegawaiProfileDao(): PegawaiProfileDao
}

