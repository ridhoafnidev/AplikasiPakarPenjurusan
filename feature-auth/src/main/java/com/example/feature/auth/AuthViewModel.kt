package com.example.feature.auth

import androidx.lifecycle.*
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.request.guru.RegisterGuruRequest
import com.example.core_data.api.request.siswa.RegisterSiswaRequest
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.domain.*
import com.example.core_data.repository.AuthRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val auth: LiveData<User?> = liveData<User?> {
        emit(authRepository.getAuth())
    }

    val guruDetail: LiveData<Guru?> = liveData<Guru?> {
        emit(authRepository.getGuruDetail(idUser.toInt()))
    }

    val siswaDetail: LiveData<Siswa?> = liveData<Siswa?> {
        emit(authRepository.getSiswaDetail(idUser.toInt()))
    }

    var username = ""
    var password = ""
    var idUser = ""

    private val _loginRequest = MutableLiveData<ApiEvent<User?>>()
    val loginRequest: LiveData<ApiEvent<User?>> = _loginRequest

    private val _isChangePassword = MutableLiveData<ApiEvent<CommonResponse?>>()
    val isChangePassword: LiveData<ApiEvent<CommonResponse?>> = _isChangePassword

    private val _guruRequest = MutableLiveData<ApiEvent<Guru?>>()
    val guruRequest: LiveData<ApiEvent<Guru?>> = _guruRequest

    private val _siswaRequest = MutableLiveData<ApiEvent<Siswa?>>()
    val siswaRequest: LiveData<ApiEvent<Siswa?>> = _siswaRequest

    private val _registerGuruRequest = MutableLiveData<ApiEvent<RegisterGuru?>>()
    val registerGuruRequest: LiveData<ApiEvent<RegisterGuru?>> = _registerGuruRequest

    private val _registerSiswaRequest = MutableLiveData<ApiEvent<RegisterSiswa?>>()
    val registerSiswaRequest: LiveData<ApiEvent<RegisterSiswa?>> = _registerSiswaRequest

    fun login(username: String, password: String) {
        viewModelScope.launch {
            authRepository.login(username, password)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _loginRequest.value = it }
        }
    }

    fun changePassword(
        idUser: Int,
        oldPassword: String,
        newPassword: String,
    ) {
        viewModelScope.launch {
            authRepository.changePassword(idUser, oldPassword, newPassword)
                .onStart {
                    emit(ApiEvent.OnProgress())
                }
                .collect {
                    _isChangePassword.value = it
                }
        }
    }

    fun getGuruById(
        idUser: Int,
    ) {
        viewModelScope.launch {
            authRepository.getGuruById(idUser)
                .onStart {
                    emit(ApiEvent.OnProgress())
                }
                .collect {
                    _guruRequest.value = it
                }
        }
    }

    fun getSiswaById(
        idUser: Int,
    ) {
        viewModelScope.launch {
            authRepository.getSiswaById(idUser)
                .onStart {
                    emit(ApiEvent.OnProgress())
                }
                .collect {
                    _siswaRequest.value = it
                }
        }
    }

    fun registerGuru(registerGuruRequest: RegisterGuruRequest) {
        viewModelScope.launch {
            authRepository.registerGuru(registerGuruRequest)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _registerGuruRequest.value = it }
        }
    }

    fun registerSiswa(registerSiswaRequest: RegisterSiswaRequest) {
        viewModelScope.launch {
            authRepository.registerSiswa(registerSiswaRequest)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _registerSiswaRequest.value = it }
        }
    }

}