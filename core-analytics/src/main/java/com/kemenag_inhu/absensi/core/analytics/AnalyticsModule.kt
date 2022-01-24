package com.kemenag_inhu.absensi.core.analytics

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val analyticsModule
    get() = module {
        single { Firebase.analytics }
        single { Firebase.crashlytics }
        single { Analytics(get(), get()) }
    }