package com.example.feature.profile

import android.util.Log
import androidx.navigation.findNavController
import com.example.core_navigation.ModuleNavigator
import com.example.core_util.base.BaseActivity
import com.example.feature.profile.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate),
    ModuleNavigator.ProfileNav {

    private val navController by lazy { findNavController(R.id.profile_navigation) }
    private val toolbar by lazy { binding.componentToolbar.toolbar }

    val status by statusParam()
    val idUserSiswa by idUserSiswaParam()

    override fun initView() {
//        toolbar.title = "Profil"
        Log.d("sdsd", "sdsdsdsdsd $status")
        setSupportActionBar(toolbar)
        setPageName("Profil")
    }

    override fun initListener() {

    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}