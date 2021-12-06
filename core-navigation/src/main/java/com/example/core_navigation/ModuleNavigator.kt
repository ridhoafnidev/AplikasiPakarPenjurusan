package com.example.core_navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface ModuleNavigator{

    fun <T> T.navigateToAuthActivity(
        finnishCurrent: Boolean = false
    ) where T : Fragment, T : ModuleNavigator {
        startActivity(ActivityClassPath.Auth, finnishCurrent)
    }

    fun <T> T.navigateToAuthActivity(
        finnishCurrent: Boolean = false
    ) where T : AppCompatActivity, T : ModuleNavigator {
        startActivity(ActivityClassPath.Auth, finnishCurrent)
    }

}

//region Extension functions to start activity in Activities list without parameter

private fun AppCompatActivity.startActivity(intent: Intent, finnishCurrent: Boolean) {
    startActivity(intent)
    if (finnishCurrent) finish()
}

private fun AppCompatActivity.startActivity(
    activityClassPath: ActivityClassPath,
    finnishCurrent: Boolean
) = startActivity(activityClassPath.getIntent(this), finnishCurrent)

private fun Fragment.startActivity(intent: Intent, finnishCurrent: Boolean) {
    startActivity(intent)
    if (finnishCurrent) activity?.finish()
}

private fun Fragment.startActivity(activityClassPath: ActivityClassPath, finnishCurrent: Boolean) =
    startActivity(activityClassPath.getIntent(requireContext()), finnishCurrent)

//endregion