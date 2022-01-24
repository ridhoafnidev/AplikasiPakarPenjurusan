package com.kemenag_inhu.absensi.feature.auth.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.feature_auth.R
import com.kemenag_inhu.absensi.feature_auth.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate),
    ModuleNavigator {

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.loginFragment)
        }, 200)
    }

    override fun initListener() {

    }
}