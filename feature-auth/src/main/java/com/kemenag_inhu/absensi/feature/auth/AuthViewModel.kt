package com.kemenag_inhu.absensi.feature.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemenag_inhu.absensi.core_domain.model.Auth
import com.kemenag_inhu.absensi.core_domain.usecase.AuthUseCase
import com.kemenag_inhu.absensi.core_util.api.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    var emailOrPhoneNumber = ""
    var password = ""

    private var _isLogin = MutableLiveData<Resource<Auth>>()
    var isLogin: LiveData<Resource<Auth>> = _isLogin

    fun login() {
        val auth = Auth(
            emailOrPhoneNumber = emailOrPhoneNumber,
            password = password
        )
        viewModelScope.launch {
            authUseCase.login(auth).collect {
                _isLogin.value = it
            }
        }
    }

}