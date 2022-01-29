package com.kemenag_inhu.absensi.feature.home

import androidx.navigation.findNavController
import com.kemenag_inhu.absensi.core_resource.components.base.BaseActivity
import com.kemenag_inhu.home.R
import com.kemenag_inhu.home.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private val navController by lazy { findNavController(R.id.home_navigation) }

    override fun initView() {

    }

    override fun initListener() {
        with(binding){
            bottomNavigation.apply {
                setOnItemSelectedListener {
                    when(it.itemId){
                        R.id.home -> {
                            navController.navigate(R.id.home)
                            true
                        }
                        R.id.riwayat -> {
                            navController.navigate(R.id.riwayat)
                            true
                        }
                        R.id.profile -> {
                            navController.navigate(R.id.profile)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }
}