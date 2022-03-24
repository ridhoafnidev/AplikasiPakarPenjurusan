package com.example.feature.auth.login

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.afollestad.vvalidator.form
import com.example.core_data.api.ApiEvent
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.bindLifecycle
import com.example.core_util.dismissKeyboard
import com.example.core_util.hideProgress
import com.example.core_util.showProgress
import com.example.feature.auth.AuthViewModel
import com.example.feature_auth.R
import com.example.feature_auth.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    ModuleNavigator {

    private val textBtnLogin by lazy {
        getString(R.string.login)
    }

    private val textHintEmptyEmail by lazy {
        "Email harus diisi"
    }

    private val textHintEmptyPassword by lazy {
        "Password harus diisi"
    }

    private val viewModel by sharedViewModel<AuthViewModel>()

    override fun initView() {
        setupInput()

        viewModel.loginRequest.observe(viewLifecycleOwner, { login ->
            when (login) {
                is ApiEvent.OnProgress -> {
                    showProgress()
                    Timber.d("progress ${login.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    hideProgress(true)
                    navigateToHomeActivity(finishCurrent = true)
                }
                is ApiEvent.OnFailed -> {
                    hideProgress(true)
                    Snackbar.make(
                        requireContext(), requireView(),
                        login.getException().toString(), Snackbar.LENGTH_SHORT
                    ).show()

                }
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        )

        binding.tvGuruBk.setOnClickListener {
            val directionRegister =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(directionRegister)
        }

        binding.tvMurid.setOnClickListener {
            val directionRegister =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(directionRegister)
        }
    }

    override fun initListener() {

    }

    private fun setupInput() {
        with(binding) {
            form {
                useRealTimeValidation(disableSubmit = true)
                inputLayout(R.id.til_username) {
                    isNotEmpty().description(textHintEmptyEmail)
                }
                inputLayout(R.id.til_password) {
                    isNotEmpty().description(textHintEmptyPassword)
                }
                submitWith(R.id.btn_login) {
                    dismissKeyboard()
                    val username = edtUsername.text.toString()
                    val password = edtPassword.text.toString()

                    viewModel.username = username
                    viewModel.password = password

                    viewModel.login(username, password)
                }
            }
            btnLogin.bindLifecycle(viewLifecycleOwner)
        }
    }

    private fun showProgress() = with(binding) {
        listOf(
            btnLogin, tilUsername, tilPassword,
            edtUsername, edtPassword,
        ).forEach { it.isEnabled = false }

        btnLogin.showProgress()
    }

    private fun hideProgress(isEnable: Boolean) = with(binding) {
        btnLogin.postDelayed(
            {
                listOf(
                    btnLogin, tilUsername, tilPassword,
                    edtUsername, edtPassword,
                ).forEach { it.isEnabled = true }
            }, 1000L
        )

        btnLogin.hideProgress(textBtnLogin) {
            isEnable && with(binding) {
                "${edtUsername.text}".isNotBlank() && "${edtPassword.text}".isNotBlank()
            }
        }
    }
}