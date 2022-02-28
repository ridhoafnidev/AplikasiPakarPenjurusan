package com.example.core_navigation

import android.content.Context
import android.content.Intent

enum class ActivityClassPath(private val className: String) {
    Auth("$BASE_PATH.auth.AuthActivity"),
    Home("$BASE_PATH.home.HomeActivity"),
    Pakar("$SUB_PATH.pakar.PakarActivity"),
    Profile("$BASE_PATH.profile.ProfileActivity");

    fun getIntent(context: Context) = Intent(context, Class.forName(className))
}

private const val BASE_PATH = "com.example.feature"
private const val SUB_PATH = "com.example.subfeature"