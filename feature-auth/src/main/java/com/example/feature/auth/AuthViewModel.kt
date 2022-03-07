package com.example.feature.auth


import androidx.lifecycle.*
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.User
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

    private val _loginRequest = MutableLiveData<ApiEvent<User?>>()
    val loginRequest: LiveData<ApiEvent<User?>> = _loginRequest

    // private val _registerServiceResponse = MutableLiveData<ApiEvent<CommonResponse?>>()
    // val registerServiceResponse: LiveData<ApiEvent<CommonResponse?>> = _registerServiceResponse

    private val _registerServiceSuccess = MutableLiveData<Boolean>()
    val registerServiceSuccess: LiveData<Boolean> = _registerServiceSuccess

    private val _sendMessageRequest = MutableLiveData<ApiEvent<String?>>()
    val sendMessageRequest: LiveData<ApiEvent<String?>> = _sendMessageRequest

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _loginRequest.value = it }
        }
    }

}