package com.kemenag_inhu.absensi.feature.auth.forgot_password

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.afollestad.vvalidator.form
import com.google.android.material.snackbar.Snackbar
import com.kemenag_inhu.absensi.core_util.api.Resource
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.core_util.*
import com.kemenag_inhu.absensi.core_util.utility.dismissKeyboard
import com.kemenag_inhu.absensi.feature.auth.AuthViewModel
import com.kemenag_inhu.absensi.feature_auth.R
import com.kemenag_inhu.absensi.feature_auth.databinding.FragmentForgotPasswordBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate), ModuleNavigator {

    private val textBtnForgotPassword by lazy {
        getString(R.string.button_reset_passord)
    }

    private val textHintEmptyNikNip by lazy {
        getString(R.string.requeired_nik_nip)
    }

    private val viewModel by viewModel<AuthViewModel>()

    private lateinit var keyboardWatcher: Unregistrar

    override fun initView() {

        keyboardWatcher =
            KeyboardVisibilityEvent.registerEventListener(requireActivity()) { isOpen ->
                with(binding){
                    if (isOpen) layoutHeaderForgotPassword.root.gone()
                    else layoutHeaderForgotPassword.root.visible()
                }
            }

        // TODO change with forgot password logic
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
        //endTODO
    }

    override fun onDestroyView() {
        keyboardWatcher.unregister()
        super.onDestroyView()
    }

    override fun initListener() {
        with(binding.layoutFormForgotPassword) {
            form {
                useRealTimeValidation(disableSubmit = true)
                inputLayout(R.id.input_layout_nik_nip) {
                    isNotEmpty().description(textHintEmptyNikNip)
                }
                submitWith(R.id.btn_forgot_password) {
                    dismissKeyboard()
                    //TODO ganti dengan property nik nip forgot password
                    viewModel.emailOrPhoneNumber = edtNikNip.text.toString()

                    viewModel.login()
                }
            }
            btnForgotPassword.bindLifecycle(viewLifecycleOwner)

            tvBackToLogin.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }

        }
    }

    private fun showProgress() = with(binding.layoutFormForgotPassword) {
        listOf(
            btnForgotPassword, inputLayoutNikNip, edtNikNip
        ).forEach { it.isEnabled = false }
        btnForgotPassword.showProgress()
    }

    private fun hideProgress(isEnable: Boolean) = with(binding.layoutFormForgotPassword){
        btnForgotPassword.postDelayed(
            {
                listOf(
                    btnForgotPassword, inputLayoutNikNip, edtNikNip
                ).forEach { it.isEnabled = true }
            },1000L
        )

        btnForgotPassword.hideProgress(textBtnForgotPassword){
            isEnable && with(binding.layoutFormForgotPassword) {
                "${edtNikNip.text}".isNotBlank()
            }
        }
    }

}