package com.example.subfeature.hasilangket

import androidx.lifecycle.*
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.request.RequestAnswer
import com.example.core_data.api.request.RequestAnswerInsert
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.api.service.AnswerService
import com.example.core_data.domain.LastResult
import com.example.core_data.domain.LastResults
import com.example.core_data.domain.User
import com.example.core_data.repository.AuthRepository
import com.example.core_data.repository.LastResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HasilAngketViewModel(
    val repository: LastResultRepository,
    val authRepository: AuthRepository
    ) : ViewModel() {

    private val _lastResult = MutableLiveData<ApiEvent<LastResults?>>()
    val lastResult: LiveData<ApiEvent<LastResults?>> = _lastResult

    val auth: LiveData<User?> = liveData {
        emit(authRepository.getAuth())
    }

    fun getLastResult(siswaId: Int, isTeacher: Int) {
        viewModelScope.launch {
            repository.getLastResult(siswaId, isTeacher)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _lastResult.value = it }
        }
    }

}