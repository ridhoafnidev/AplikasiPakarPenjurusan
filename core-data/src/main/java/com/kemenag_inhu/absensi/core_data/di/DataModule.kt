package com.kemenag_inhu.absensi.core_data

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kemenag_inhu.absensi.core_data.BuildConfig.DB_NAME
import com.kemenag_inhu.absensi.core_data.data.*
import com.kemenag_inhu.absensi.core_data.data.local.*
import com.kemenag_inhu.absensi.core_data.data.local.room.CoreDatabase
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiExecutor
import com.kemenag_inhu.absensi.core_data.data.remote.api.apiClient
import com.kemenag_inhu.absensi.core_data.data.remote.api.httpClient
import com.kemenag_inhu.absensi.core_data.data.remote.service.*
import com.kemenag_inhu.absensi.core_domain.repository.*
import com.kemenag_inhu.absensi.core_util.utility.metaData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gotev.cookiestore.SharedPreferencesCookieStore
import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.CookieJar
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.Koin
import org.koin.dsl.module
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore

@Suppress("MaxLineLength")
val Application.dataModule
    get() = module {
        //region Utility

        single<CookieStore> { SharedPreferencesCookieStore(applicationContext, "edtc_coding_testing") }
        single<CookieHandler> { CookieManager(get(), CookiePolicy.ACCEPT_ALL) }
        single<CookieJar> { JavaNetCookieJar(get()) }

        single {
            httpClient(
                metaData.getInt(META_API_TIMEOUT).toLong(),
                HttpLoggingInterceptor.Level.BODY,
                @Suppress("DEPRECATION")
                ChuckerInterceptor(get())
            )
        }

        single { Moshi.Builder().build() }
        single { ApiExecutor(get()) }

        factory { apiClient<UserService>(BuildConfig.BASE_URL, get()) }

        single {
            Room.databaseBuilder(get(), CoreDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        //endregion
        //region Service


        factory { apiClient<MasterJabatanFungsionalService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterJabatanStrukturalService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterJenisTenagaService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterLevelService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterPangkatGolonganService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterPegawaiService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterPnsNonpnsService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterStatusAbsensiService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<MasterUnitKerjaService>(BuildConfig.BASE_URL, get()) }
        factory { apiClient<AbsensiService>(BuildConfig.BASE_URL, get()) }

        //endregion
        //region DAO

        single { get<CoreDatabase>().eventDao() }
        single { get<CoreDatabase>().masterJabatanFungsionalDao() }
        single { get<CoreDatabase>().masterJabatanStruktulDao() }
        single { get<CoreDatabase>().masterJenisTenagaDao() }
        single { get<CoreDatabase>().masterLevelDao() }
        single { get<CoreDatabase>().masterPangkatGolonganDao() }
        single { get<CoreDatabase>().masterPegawaiDao() }
        single { get<CoreDatabase>().masterPnsNonpnsDao() }
        single { get<CoreDatabase>().masterStatusAbsensiDao() }
        single { get<CoreDatabase>().masterUnitKerjaDao() }
        single { get<CoreDatabase>().userDao() }
        single { get<CoreDatabase>().pegawaiProfileDao() }
        single { get<CoreDatabase>().listDataAbsensi() }

        //endregion

        //region Local Datasource

        single { LocalDataSource(get()) }
        single { LocalDataSourceMasterJabatanStruktural(get()) }
        single { LocalDataSourcePegawaiProfile(get()) }
        single { LocalDataSourceMasterJabatanFungsional(get()) }
        single { LocalDataSourceMasterJenisTenaga(get()) }
        single { LocalDataSourceMasterLevel(get()) }
        single { LocalDataSourceMasterPangkatGolongan(get()) }
        single { LocalDataSourceMasterPnsNonpns(get()) }
        single { LocalDataSourceMasterStatusAbsensi(get()) }
        single { LocalDataSourceMasterUnitKerja(get()) }
        single { LocalDataSourcePegawai(get()) }
        single { LocalDataSourceUser(get()) }
        single { LocalDataSourceAbsensi(get()) }

        //endregion
        //region Repository

        single<IEventDbRepository> { EventRepository(get()) }
        single<IAuthRepository> { AuthRepository() }

        single { UserRepository(get(), get(), get()) }
        single { MasterJabatanStrukturalRepository(get(), get(), get()) }
        single { PegawaiProfileRepository(get(), get(), get(), get()) }
        single { MasterJabatanFungsionalRepository(get(), get(), get()) }
        single { AbsensiRepository(get(), get(), get(), get()) }
        single { MasterJenisTenagaRepository(get(), get(), get()) }
        single { MasterLevelRepository(get(), get(), get()) }
        single { MasterPangkatGolonganRepository(get(), get(), get()) }
        single { MasterPegawaiRepository(get(), get(), get()) }
        single { MasterPnsNonpnsRepository(get(), get(), get()) }
        single { MasterStatusAbsensiRepository(get(), get(), get()) }
        single { MasterUnitKerjaRepository(get(), get(), get()) }

        //endregion
    }

private const val META_APP_ID        = "APP_ID"
private const val TIMEOUT            = 30L
private const val META_API_TIMEOUT   = "API_TIMEOUT_SECOND"

/**
 * Clear local database
 */
suspend fun Koin.clearAppData() {
    withContext(Dispatchers.IO) {
        getOrNull<CoreDatabase>()?.clearAllTables()
        getOrNull<SharedPreferences>()?.edit { clear() }
    }
}

fun CookieHandler.removeAll() {
    (this as CookieManager).cookieStore.removeAll()
}