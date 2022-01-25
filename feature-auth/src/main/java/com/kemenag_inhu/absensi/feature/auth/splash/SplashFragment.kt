package com.kemenag_inhu.absensi.feature.auth.splash

import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.feature_auth.R
import com.kemenag_inhu.absensi.feature_auth.databinding.FragmentSplashBinding


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate),
    ModuleNavigator {

    override fun initView() {
        animate()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.loginFragment)
        }, 2000)
    }

    override fun initListener() {

    }

    private fun animate() {
        val rotateImage = AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.fade_in
        )
        binding.ivLogo.startAnimation(rotateImage)

        // Log.v("test","animation slide");
    }

}