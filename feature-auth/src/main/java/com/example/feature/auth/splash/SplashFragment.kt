package com.example.feature.auth.splash

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.core_data.domain.isGuru
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.AuthViewModel
import com.example.feature.auth.goToLogin
import com.example.feature_auth.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate), ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            observeAuth()
        }, 2000)
    }

    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner){ data ->
            data?.let {
                if (data.isCurrent){
                    data.let {
                        if (it.isGuru) {
                        }
                        navigateToHomeActivity(true)
                    }
                }
            } ?: goToLogin()
        }
    }

    override fun initListener() {

    }
}