package com.example.feature.auth.splash

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.isGuru
import com.example.core_data.domain.isSiswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.AuthViewModel
import com.example.feature.auth.goToLogin
import com.example.feature_auth.databinding.FragmentSplashBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate),
    ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()

    override fun initView() {
        //(activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            observeAuth()
        }, 2000)
    }

    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            data?.let {
                Log.d("sdsdsd", "cureesdsd ${data.isCurrent}")
                if (data.isCurrent) {
                    data.let {
                        if (it.isGuru) {
                            viewModel.getGuruById(it.idUser.toInt())

                            viewModel.guruRequest.observe(viewLifecycleOwner, { guru ->
                                when (guru) {
                                    is ApiEvent.OnProgress -> {
                                        Timber.d("progress ${guru.currentResult}")
                                    }
                                    is ApiEvent.OnSuccess -> {
                                        navigateToHomeActivity(finishCurrent = true)
                                    }
                                    is ApiEvent.OnFailed -> {
                                        Snackbar.make(
                                            requireContext(), requireView(),
                                            guru.getException().toString(), Snackbar.LENGTH_SHORT
                                        ).show()
                                        Log.d(
                                            "sdsdsd",
                                            "cureesdsd ${guru.getException().toString()}"
                                        )
                                    }
                                }
                            })
                        } else if (it.isSiswa) {
                            viewModel.getSiswaById(it.idUser.toInt())

                            viewModel.siswaRequest.observe(viewLifecycleOwner, { siswa ->
                                when (siswa) {
                                    is ApiEvent.OnProgress -> {
                                        Timber.d("progress ${siswa.currentResult}")
                                    }
                                    is ApiEvent.OnSuccess -> {
                                        navigateToHomeActivity(finishCurrent = true)
                                    }
                                    is ApiEvent.OnFailed -> {
                                        Snackbar.make(
                                            requireContext(), requireView(),
                                            siswa.getException().toString(), Snackbar.LENGTH_SHORT
                                        ).show()
                                        Log.d(
                                            "sdsdsd",
                                            "cureesdsd ${siswa.getException().toString()}"
                                        )
                                    }
                                }
                            })
                        }
                    }
                }
            } ?: goToLogin()
        }
    }

    override fun initListener() {

    }
}