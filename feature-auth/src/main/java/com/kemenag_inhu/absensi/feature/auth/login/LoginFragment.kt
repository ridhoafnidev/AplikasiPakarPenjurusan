package com.kemenag_inhu.absensi.feature.auth.login


import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.afollestad.vvalidator.form
import com.google.android.material.snackbar.Snackbar
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.core_util.*
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiEvent
import com.kemenag_inhu.absensi.core_util.utility.dismissKeyboard
import com.kemenag_inhu.absensi.feature_auth.R
import com.kemenag_inhu.absensi.feature_auth.databinding.FragmentLoginBinding
import com.kemenag_inhu.absensi.subfeature.dialog.apifailed.showApiFailedDialog
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate), ModuleNavigator {

    private val textBtnLogin by lazy {
        getString(R.string.text_login)
    }

    private val textHintEmptyEmailPhoneNumber by lazy {
        getString(R.string.requeired_nik_nip)
    }

    private val textHintEmptyPassword by lazy {
        getString(R.string.required_password)
    }


    private val viewModel: LoginViewModel by sharedViewModel()

    private lateinit var keyboardWatcher: Unregistrar

    override fun initView() {
        keyboardWatcher =
            KeyboardVisibilityEvent .registerEventListener(requireActivity()) { isOpen ->
            with(binding) {
                if (isOpen) {
                    layoutFooterlogin.root.gone()
                    layoutHeaderLogin.root.gone()
                } else {
                    layoutFooterlogin.root.visible()
                    layoutHeaderLogin.root.visible()
                    layoutFormLogin.edtEmailOrNumberPhone.clearFocus()
                    layoutFormLogin.edtPassword.clearFocus()
                }
            }
        }

        onBackpress()

    }

    private fun onBackpress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        )
    }

    private fun loginObserver() {

    }

    override fun onDestroyView() {
        keyboardWatcher.unregister()
        super.onDestroyView()
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
                    viewModel.login(edtEmailOrNumberPhone.text.toString(), edtPassword.text.toString())
                    viewModel.login.observe(viewLifecycleOwner){ auth ->
                        when(auth){
                            is ApiEvent.OnLoading -> {
                                showProgress()
                            }
                            is ApiEvent.OnSuccess -> {
                                    navigateToHomeActivity()
                            }
                            is ApiEvent.OnFailed -> {
                                Handler(Looper.getMainLooper()).postDelayed({
                                    hideProgress(true)
                                }, 1000L)
                                auth?.getException()?.let { errorMessage ->
                                    showApiFailedDialog(errorMessage)
                                }
                            }
                        }
                    }
                    //loginObserver()
                }
            }

            tvForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }

        }

        binding.layoutFooterlogin.btnLogin.bindLifecycle(viewLifecycleOwner)

    }

    private fun showProgress() = with(binding.layoutFormLogin) {
        listOf(
            binding.layoutFooterlogin.btnLogin, inputLayoutEmailOrNumberPhone, inputLayoutPassword
        ).forEach { it.isEnabled = false }
        binding.layoutFooterlogin.btnLogin.showProgress()
    }

    private fun hideProgress(isEnable: Boolean) = with(binding.layoutFormLogin){
        binding.layoutFooterlogin.btnLogin.postDelayed(
            {
                listOf(
                    binding.layoutFooterlogin.btnLogin, edtEmailOrNumberPhone, edtPassword, inputLayoutEmailOrNumberPhone,
                    inputLayoutPassword
                ).forEach { it.isEnabled = true }
            },1000L
        )

        binding.layoutFooterlogin.btnLogin.hideProgress(textBtnLogin){
            isEnable && with(binding.layoutFormLogin) {
                "${edtEmailOrNumberPhone.text}".isNotBlank() && "${edtPassword.text}".isNotBlank()
            }
        }
    }

}