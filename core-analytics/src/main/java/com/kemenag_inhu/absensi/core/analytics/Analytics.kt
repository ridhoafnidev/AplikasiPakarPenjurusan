package com.kemenag_inhu.absensi.core.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.lang.Exception

class Analytics(
    private val analyticsTracker: FirebaseAnalytics,
    private val crashTracker: FirebaseCrashlytics
) {

    // region Analytics& Crash Tracker

    fun setUser(userId: Int){
        crashTracker.setUserId("$userId")
        analyticsTracker.setUserId("$userId")
    }

    fun removeUser() {
        crashTracker.setUserId("")
        analyticsTracker.setUserId(null)
    }

    // endregion
    // region Analytics Tracker

    fun logEvent(name: String, parameter: Bundle) {
        analyticsTracker.logEvent(name, parameter)
    }

    // endregion
    // region Crash Tracker

    fun logException(exception: Exception){
        crashTracker.recordException(exception)
    }

    fun setCustomKey(key: String, value: String){
        crashTracker.setCustomKey(key, value)
    }

    fun setCustomKey(key: String, value: Boolean){
        crashTracker.setCustomKey(key, value)
    }

    fun setCustomKey(key: String, value: Int){
        crashTracker.setCustomKey(key, value)
    }

    fun setCustomKey(key: String, value: Long){
        crashTracker.setCustomKey(key, value)
    }

    fun setCustomKey(key: String, value: Double){
        crashTracker.setCustomKey(key, value)
    }

    fun logMessage(message: String){
        crashTracker.log(message)
    }

    // endregion

}