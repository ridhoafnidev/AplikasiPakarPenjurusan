package com.kemenag_inhu.absensi

import android.app.Application
import com.kemenag_inhu.absensi.core_data.dataModule
import com.kemenag_inhu.absensi.core_data.di.domainModule
import com.kemenag_inhu.absensi.feature.auth.authModule
import com.kemenag_inhu.absensi.feature.home.homeModule
import com.kemenag_inhu.absensi.subfeature.create_event.createEventModule
import io.armcha.debugBanner.Banner
import io.armcha.debugBanner.DebugBanner
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        when (BuildConfig.BUILD_TYPE) {
            "debug" -> "DEV"
            else -> ""
        }.let {
            val banner = Banner(bannerText = it, bannerColorRes = R.color.colorEnvBanner)
            DebugBanner.init(this, banner)
        }


        when(BuildConfig.BUILD_TYPE) {
            "debug" -> Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    authModule,
                    domainModule,
                    createEventModule,
                    homeModule
                )
            )
        }
    }
}