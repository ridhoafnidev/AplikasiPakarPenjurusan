package com.example.feature.home

import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.core_util.base.BaseActivity
import com.example.home.R
import com.example.home.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private val navController by lazy { findNavController(R.id.home_navigation) }
    private val toolbar by lazy { binding.componentToolbar.toolbar }

    override fun initView() {

        setSupportActionBar(toolbar as Toolbar)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.home, R.id.profile
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.apply {
            setOnItemSelectedListener {
               when(it.itemId){
                    R.id.home -> {
                        navController.navigate(R.id.home)
                        true
                    }
                    R.id.profile -> {
                        navController.navigate(R.id.profile)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    override fun initListener() {
    }
}