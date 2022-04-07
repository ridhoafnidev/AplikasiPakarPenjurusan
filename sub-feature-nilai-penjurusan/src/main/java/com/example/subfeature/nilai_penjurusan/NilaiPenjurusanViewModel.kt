package com.example.subfeature.nilai_penjurusan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.LastResults
import com.example.core_data.domain.ListNilaiSiswa
import com.example.core_data.repository.LastResultRepository
import com.example.core_data.repository.NilaiSiswaRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NilaiPenjurusanViewModel(
    private val nilaiSiswaRepository: NilaiSiswaRepository,
    private val lastResultRepository: LastResultRepository
) : ViewModel() {

    private val _nilaiSiswaGetAll = MutableLiveData<ApiEvent<ListNilaiSiswa?>>()
    val nilaiSiswaGetAll: LiveData<ApiEvent<ListNilaiSiswa?>> = _nilaiSiswaGetAll

    private val _lastResultAll = MutableLiveData<ApiEvent<LastResults?>>()
    val lastResultAll: LiveData<ApiEvent<LastResults?>> = _lastResultAll

    init {
        getNilaiSiswaAll()
        getLastResultAll()
    }

    fun getNilaiSiswaAll() {
        viewModelScope.launch {
            nilaiSiswaRepository.getNilaiSiswaAll()
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _nilaiSiswaGetAll.value = it }
        }
    }

    fun getLastResultAll() {
        viewModelScope.launch {
            lastResultRepository.getLastResult(0, 1)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _lastResultAll.value = it }
        }
    }
}