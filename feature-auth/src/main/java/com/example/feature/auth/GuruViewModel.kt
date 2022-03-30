package com.example.feature.auth

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.request.guru.UpdateGuruRequest
import com.example.core_data.domain.Guru
import com.example.core_data.repository.GuruRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GuruViewModel(
    private val guruRepository: GuruRepository
) : ViewModel() {

    private val _uploadFotoGuru = MutableLiveData<ApiEvent<Guru?>>()
    val uploadFotoGuru: LiveData<ApiEvent<Guru?>> = _uploadFotoGuru

    private val _updateGuru = MutableLiveData<ApiEvent<Guru?>>()
    val updateGuru: LiveData<ApiEvent<Guru?>> = _updateGuru

    @RequiresApi(Build.VERSION_CODES.Q)
    fun updateFotoGuru(
        id: Int,
        filePath: String,
        uri: Uri,
        contentResolver: ContentResolver,
        context: Context
    ) {
        viewModelScope.launch {
            guruRepository.updateFotoGuru(id, filePath, uri, contentResolver, context)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _uploadFotoGuru.value = it }
        }
    }

    fun updateGuru(idUser: Int, updateGuruRequest: UpdateGuruRequest) {
        viewModelScope.launch {
            guruRepository.updateGuru(idUser, updateGuruRequest)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _updateGuru.value = it }
        }
    }
}