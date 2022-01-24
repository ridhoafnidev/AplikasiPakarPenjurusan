package com.kemenag_inhu.absensi.core_navigation

import android.content.Context
import android.content.Intent

enum class ActivityClassPath(private val className: String) {
    Auth("$BASE_PATH.auth.AuthActivity"),
    Home("$BASE_PATH.home.HomeActivity"),
    CreateEvent("$SUB_PATH.create_event.CreateEventActivity");

    fun getIntent(context: Context) = Intent(context, Class.forName(className))
}

private const val BASE_PATH = "com.ridhoafnidev.edtc.feature"
private const val SUB_PATH = "com.ridhoafnidev.edtc.subfeature"