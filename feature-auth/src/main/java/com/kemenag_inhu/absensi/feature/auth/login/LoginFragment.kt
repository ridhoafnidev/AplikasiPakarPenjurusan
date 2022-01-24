package com.kemenag_inhu.absensi.feature.auth.login

import android.os.Handler
import android.os.Looper
import com.afollestad.vvalidator.form
import com.google.android.material.snackbar.Snackbar
import com.kemenag_inhu.absensi.core_util.Resource
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.core_util.*
import com.kemenag_inhu.absensi.feature.auth.AuthViewModel
import com.kemenag_inhu.absensi.feature_auth.R
import com.kemenag_inhu.absensi.feature_auth.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate), ModuleNavigator {

    private val textBtnLogin by lazy {
        getString(R.string.text_login)
    }

    private val textHintEmptyEmailPhoneNumber by lazy {
        getString(R.string.requeired_email_or_phone_number)
    }

    private val textHintEmptyPassword by lazy {
        getString(R.string.required_password)
    }

    private val viewModel by viewModel<AuthViewModel>()

    override fun initView() {
        viewModel.isLogin.observe(viewLifecycleOwner){ auth ->
            when(auth){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        hideProgress(true)
                        navigateToHomeActivity()
                    }, 1000L)
                }
                is Resource.Error -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        hideProgress(true)
                    }, 1000L)
                    auth?.message?.let { errorMessage ->
                        showSnackBar(requireContext(), binding.parentLogin, errorMessage, Snackbar.LENGTH_LONG)
                    }
                }
            }
        }
    }

    override fun initListener() {
        with(binding.layoutFormLogin) {
            form {
                useRealTimeValidation(disableSubmit = true)
                inputLayout(R.id.input_layout_email_or_number_phone) {
                    isNotEmpty().description(textHintEmptyEmailPhoneNumber)
                }
                inputLayout(R.id.input_layout_password) {
                    isNotEmpty().description(textHintEmptyPassword)
                }
                submitWith(R.id.btn_login) {
                    dismissKeyboard()

                    viewModel.emailOrPhoneNumber = edtEmailOrNumberPhone.text.toString()
                    viewModel.password = edtPassword.text.toString()

                    viewModel.login()
                }
            }
            btnLogin.bindLifecycle(viewLifecycleOwner)
        }
    }

    private fun showProgress() = with(binding.layoutFormLogin) {
        listOf(
            btnLogin, inputLayoutEmailOrNumberPhone, inputLayoutPassword
        ).forEach { it.isEnabled = false }
        btnLogin.showProgress()
    }

    private fun hideProgress(isEnable: Boolean) = with(binding.layoutFormLogin){
        btnLogin.postDelayed(
            {
                listOf(
                    btnLogin, edtEmailOrNumberPhone, edtPassword, inputLayoutEmailOrNumberPhone,
                    inputLayoutPassword
                ).forEach { it.isEnabled = true }
            },1000L
        )

        btnLogin.hideProgress(textBtnLogin){
            isEnable && with(binding.layoutFormLogin) {
                "${edtEmailOrNumberPhone.text}".isNotBlank() && "${edtPassword.text}".isNotBlank()
            }
        }
    }

}