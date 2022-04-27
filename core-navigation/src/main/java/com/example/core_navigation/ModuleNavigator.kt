package com.example.core_navigation

import android.content.Intent
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.core_navigation.ModuleNavigator.NilaiPenjurusanNav.Companion.USER_ID

interface ModuleNavigator {

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

    fun <T> T.navigateToHomeActivity(
        finishCurrent: Boolean = false
    ) where T : AppCompatActivity, T : ModuleNavigator {
        startActivity(ActivityClassPath.Home, finishCurrent)
    }

    fun <T> T.navigateToHomeActivity(
        finishCurrent: Boolean = false
    ) where T : Fragment, T : ModuleNavigator {
        startActivity(ActivityClassPath.Home, finishCurrent)
    }

    fun <T> T.navigateToPakarActivity(
        finishCurrent: Boolean = false
    ) where T : Fragment, T : ModuleNavigator {
        startActivity(ActivityClassPath.Pakar, finishCurrent)
    }

    fun <T> T.navigateToPakarActivity(
        finishCurrent: Boolean = false
    ) where T : AppCompatActivity, T : ModuleNavigator {
        startActivity(ActivityClassPath.Pakar, finishCurrent)
    }

    fun <T> T.navigateToHasilAngketActivity(
        finishCurrent: Boolean = false
    ) where T : Fragment, T : ModuleNavigator {
        startActivity(ActivityClassPath.HasilAngket, finishCurrent)
    }

    fun <T> T.navigateToHasilAngketActivity(
        finishCurrent: Boolean = false
    ) where T : AppCompatActivity, T : ModuleNavigator {
        startActivity(ActivityClassPath.HasilAngket, finishCurrent)
    }

    interface ProfileNav : ModuleNavigator {

        companion object {
            const val STATUS = "status"
            const val ID_USER_SISWA = "id user siswa"
        }

        @MainThread
        fun <T> T.statusParam(): Lazy<String> where T : AppCompatActivity, T : ProfileNav =
            lazy(LazyThreadSafetyMode.NONE) {
                intent.getStringExtra(STATUS).orEmpty()
            }

        @MainThread
        fun <T> T.idUserSiswaParam(): Lazy<String> where T : AppCompatActivity, T : ProfileNav =
            lazy(LazyThreadSafetyMode.NONE) {
                intent.getStringExtra(ID_USER_SISWA).orEmpty()
            }
    }

    fun <T> T.navigateToProfileActivity(
        finishCurrent: Boolean = false,
        status: String = "",
        idUserSiswa: String = ""
    ) where T : Fragment, T : ModuleNavigator {
        ActivityClassPath.Profile.getIntent(requireContext())
            .apply {
                putExtra(ProfileNav.STATUS, status)
                putExtra(ProfileNav.ID_USER_SISWA, idUserSiswa)
            }.let { startActivity(it, finishCurrent) }
    }

    fun <T> T.navigateToProfileActivity(
        finishCurrent: Boolean = false,
        status: String = "",
        idUserSiswa: String = ""
    ) where T : AppCompatActivity, T : ModuleNavigator {
        ActivityClassPath.Profile.getIntent(this)
            .apply {
                putExtra(ProfileNav.STATUS, status)
                putExtra(ProfileNav.ID_USER_SISWA, idUserSiswa)
            }.let { startActivity(it, finishCurrent) }
    }


    interface NilaiSiswaNav : ModuleNavigator {

        companion object {
            const val LEVEL = "level"
        }

        @MainThread
        fun <T> T.levelParam(): Lazy<String> where T : AppCompatActivity, T : NilaiSiswaNav =
            lazy(LazyThreadSafetyMode.NONE) {
                intent.getStringExtra(LEVEL).orEmpty()
            }

        @MainThread
        fun <T> T.userIdParam(): Lazy<String> where T : AppCompatActivity, T : NilaiSiswaNav =
            lazy(LazyThreadSafetyMode.NONE) {
                intent.getStringExtra(USER_ID).orEmpty()
            }
    }

    //region Nilai Penjurusan

    interface NilaiPenjurusanNav : ModuleNavigator {

        companion object {
            const val LEVEL = "level"
            const val USER_ID = "user_id"
        }

        @MainThread
        fun <T> T.levelParam(): Lazy<String> where T : AppCompatActivity, T : NilaiPenjurusanNav =
            lazy(LazyThreadSafetyMode.NONE) {
                intent.getStringExtra(LEVEL).orEmpty()
            }

        @MainThread
        fun <T> T.userIdParam(): Lazy<String> where T : AppCompatActivity, T : NilaiPenjurusanNav =
            lazy(LazyThreadSafetyMode.NONE) {
                intent.getStringExtra(USER_ID).orEmpty()
            }
    }

    fun <T> T.navigateToNilaiPenjurusanActivity(
        finishCurrent: Boolean = false,
        level: String = "",
        userId: String = ""
    ) where T : Fragment, T : ModuleNavigator {
        ActivityClassPath.NilaiPenjurusan.getIntent(requireContext())
            .apply {
                putExtra(NilaiPenjurusanNav.LEVEL, level)
                putExtra(NilaiPenjurusanNav.USER_ID, userId)
            }.let { startActivity(it, finishCurrent) }
    }

    fun <T> T.navigateToNilaiPenjurusanActivity(
        finishCurrent: Boolean = false,
        level: String = "",
        userId: String = ""
    ) where T : AppCompatActivity, T : ModuleNavigator {
        ActivityClassPath.NilaiPenjurusan.getIntent(this)
            .apply {
                putExtra(NilaiPenjurusanNav.LEVEL, level)
                putExtra(NilaiPenjurusanNav.USER_ID, userId)
            }.let { startActivity(it, finishCurrent) }
    }

    //endregion

    fun <T> T.navigateToNilaiSiswaActivity(
        finishCurrent: Boolean = false,
        level: String = ""
    ) where T : Fragment, T : ModuleNavigator {
        ActivityClassPath.NilaiSiswa.getIntent(requireContext())
            .apply {
                putExtra(NilaiSiswaNav.LEVEL, level)
            }.let { startActivity(it, finishCurrent) }
    }

    fun <T> T.navigateToNilaiSiswaActivity(
        finishCurrent: Boolean = false,
        level: String = ""
    ) where T : AppCompatActivity, T : ModuleNavigator {
        ActivityClassPath.NilaiSiswa.getIntent(this)
            .apply {
                putExtra(NilaiSiswaNav.LEVEL, level)
            }.let { startActivity(it, finishCurrent) }
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