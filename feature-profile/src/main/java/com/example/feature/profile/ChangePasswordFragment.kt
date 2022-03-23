package com.example.feature.profile

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import com.afollestad.vvalidator.form
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.isGuru
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.bindLifecycle
import com.example.core_util.dismissKeyboard
import com.example.core_util.hideProgress
import com.example.core_util.showProgress
import com.example.feature.auth.AuthViewModel
import com.example.feature.auth.goToLogin
import com.example.feature.profile.databinding.FragmentChangePasswordBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding>(FragmentChangePasswordBinding::inflate),
    ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()

    private var idUser = ""

    private val textBtnChangePassword by lazy {
        "Ganti Password"
    }

    private val textHintEmptyOldPassword by lazy {
        "Password lama harus diisi"
    }

    private val textHintEmptyNewPassword by lazy {
        "Password baru harus diisi"
    }

    private val textHintEmptyConfirmNewPassword by lazy {
        "Konfirmasi Password baru harus diisi"
    }

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (!data?.idUser.toString().isEmpty()) {
                idUser = data?.idUser.toString()
            }
        }

        setupInput()

        viewModel.isChangePassword.observe(viewLifecycleOwner, { login ->
            when (login) {
                is ApiEvent.OnProgress -> {
                    showProgress()
                    Timber.d("progress ${login.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    hideProgress(true)
//                    navigateToHomeActivity(finishCurrent = true)
                }
                is ApiEvent.OnFailed -> {
                    Log.d("sdsdsd", "cureesdsd ${login.getException().toString()}")
                    hideProgress(true)
                    Snackbar.make(
                        requireContext(), requireView(),
                        login.getException().toString(), Snackbar.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }

    override fun initListener() {

    }

    private fun setupInput() {
        with(binding) {
            form {
                useRealTimeValidation(disableSubmit = true)
                inputLayout(R.id.til_old_password) {
                    isNotEmpty().description(textHintEmptyOldPassword)
                }
                inputLayout(R.id.til_new_password) {
                    isNotEmpty().description(textHintEmptyNewPassword)
                }
                inputLayout(R.id.til_confirm_password) {
                    isNotEmpty().description(textHintEmptyConfirmNewPassword)
                }
                submitWith(R.id.btn_change_password) {
                    dismissKeyboard()
                    val oldPassword = edtOldPassword.text.toString()
                    val newPassword = edtNewPassword.text.toString()
                    val confirmPassword = edtConfirmPassword.text.toString()

                    if (newPassword != confirmPassword) {
                        Toast.makeText(
                            context,
                            "Password baru dan konfirmasi password tidak sama",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.changePassword(idUser.toInt(), oldPassword, newPassword)
                    }
                }
            }
            btnChangePassword.bindLifecycle(viewLifecycleOwner)
        }
    }

    private fun showProgress() = with(binding) {
        listOf(
            btnChangePassword, tilOldPassword, tilNewPassword, tilConfirmPassword,
            edtOldPassword, edtNewPassword, edtConfirmPassword
        ).forEach { it.isEnabled = false }

        btnChangePassword.showProgress()
    }

    private fun hideProgress(isEnable: Boolean) = with(binding) {
        btnChangePassword.postDelayed(
            {
                listOf(
                    btnChangePassword, tilOldPassword, tilNewPassword, tilConfirmPassword,
                    edtOldPassword, edtNewPassword, edtConfirmPassword
                ).forEach { it.isEnabled = true }
            }, 1000L
        )

        btnChangePassword.hideProgress(textBtnChangePassword) {
            isEnable && with(binding) {
                "${edtOldPassword.text}".isNotBlank() && "${edtNewPassword.text}".isNotBlank()
                        && "${edtConfirmPassword.text}".isNotBlank()
            }
        }
    }
}