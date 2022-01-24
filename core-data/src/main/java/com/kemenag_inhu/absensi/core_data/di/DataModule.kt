package com.kemenag_inhu.absensi.core_data

import android.app.Application
import androidx.room.Room
import com.kemenag_inhu.absensi.core_data.BuildConfig.DB_NAME
import com.kemenag_inhu.absensi.core_data.data.AuthRepository
import com.kemenag_inhu.absensi.core_data.data.EventRepository
import com.kemenag_inhu.absensi.core_domain.repository.IAuthRepository
import com.kemenag_inhu.absensi.core_domain.repository.IEventDbRepository
import com.kemenag_inhu.absensi.core_data.data.local.LocalDataSource
import com.kemenag_inhu.absensi.core_data.data.local.room.CoreDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gotev.cookiestore.SharedPreferencesCookieStore
import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.CookieJar
import org.koin.core.Koin
import org.koin.dsl.module
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore

@Suppress("MaxLineLength")
val Application.dataModule
    get() = module {
        single<CookieStore> { SharedPreferencesCookieStore(applicationContext, "edtc_coding_testing") }
        single<CookieHandler> { CookieManager(get(), CookiePolicy.ACCEPT_ALL) }
        single<CookieJar> { JavaNetCookieJar(get()) }
        single {
            Room.databaseBuilder(get(), CoreDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<CoreDatabase>().eventDao() }
        single { LocalDataSource(get()) }
        single<IEventDbRepository> { EventRepository(get()) }
        single<IAuthRepository> { AuthRepository() }
    }

/**
 * Clear local database
 */
suspend fun Koin.clearAppData() {
    withContext(Dispatchers.IO) {
        getOrNull<CoreDatabase>()?.clearAllTables()
    }
}

fun CookieHandler.removeAll() {
    (this as CookieManager).cookieStore.removeAll()
}