package com.kemenag_inhu.absensi.feature.auth

import androidx.navigation.findNavController
import com.kemenag_inhu.absensi.core_resource.components.base.BaseActivity
import com.kemenag_inhu.absensi.core_util.setSystemBarColor
import com.kemenag_inhu.absensi.feature_auth.R
import com.kemenag_inhu.absensi.feature_auth.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    private val navController by lazy { findNavController(R.id.activity_auth_nav_host_fragment) }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun initView() {
        setSystemBarColor(R.color.colorBackgroundSecondary)
    }

    override fun initListener() {
    }
}