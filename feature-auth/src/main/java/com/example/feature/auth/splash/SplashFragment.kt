package com.example.feature.auth.splash

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature_auth.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate), ModuleNavigator {

    override fun initView() {
        //(activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToHomeActivity(true)
        }, 200)
    }

    override fun initListener() {

    }
}