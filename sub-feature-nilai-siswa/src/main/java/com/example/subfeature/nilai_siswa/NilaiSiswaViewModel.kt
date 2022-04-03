package com.example.subfeature.nilai_siswa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.ListNilaiSiswa
import com.example.core_data.repository.NilaiSiswaRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NilaiSiswaViewModel(
    private val nilaiSiswaRepository: NilaiSiswaRepository
) : ViewModel() {

//    val auth: LiveData<User?> = liveData<User?> {
//        emit(authRepository.getAuth())
//    }

    private val _nilaiSiswaGetAll = MutableLiveData<ApiEvent<ListNilaiSiswa?>>()
    val nilaiSiswaGetAll: LiveData<ApiEvent<ListNilaiSiswa?>> = _nilaiSiswaGetAll

    private val _deleteSiswa = MutableLiveData<ApiEvent<Unit?>>()
    val deleteSiswa: LiveData<ApiEvent<Unit?>> = _deleteSiswa

    fun getNilaiSiswaAll() {
        viewModelScope.launch {
            nilaiSiswaRepository.getNilaiSiswaAll()
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _nilaiSiswaGetAll.value = it }
        }
    }

    fun deleteSiswa(idUser: Int) {
        viewModelScope.launch {
            nilaiSiswaRepository.deleteNilaiSiswa(idUser)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _deleteSiswa.value = it }
        }
    }
}