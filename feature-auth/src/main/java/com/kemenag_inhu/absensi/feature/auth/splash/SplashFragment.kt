package com.kemenag_inhu.absensi.feature.auth.splash

import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.kemenag_inhu.absensi.core.analytics.Analytics
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.feature.auth.goToLogin
import com.kemenag_inhu.absensi.feature.auth.login.LoginViewModel
import com.kemenag_inhu.absensi.feature_auth.R
import com.kemenag_inhu.absensi.feature_auth.databinding.FragmentSplashBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate),
    ModuleNavigator {


    //region View Model

    private val viewModel by sharedViewModel<LoginViewModel>()

    //endregion
    //region Analytics

    private val analytics: Analytics by inject()

    //endregion
    //region PIN Lock Flag Helper

    private var isPendingGoToHome = false
    private var isPaused = false

    //endregion

    override fun initView() {
        animate()
        Handler(Looper.getMainLooper()).postDelayed({
            observeUser()
        }, 2000)
    }


    private fun observeUser() {
        viewModel.currentUser.run {
            observe(viewLifecycleOwner, {
                removeObservers(viewLifecycleOwner)
                it?.let { user ->
                    analytics.setUser(user.idUser)
                    if (!isPaused) navigateToHomeActivity(finishCurrent = true)
                    else isPendingGoToHome = true
                } ?: goToLogin()
            })
        }
    }

    override fun initListener() {

    }

    override fun onResume() {
        super.onResume()
        isPaused = false
        if (isPendingGoToHome) navigateToHomeActivity(true)
    }

    override fun onPause() {
        super.onPause()
        isPaused = true
    }

    private fun animate() {
        val rotateImage = AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.fade_in
        )
        binding.ivLogo.startAnimation(rotateImage)
    }

}