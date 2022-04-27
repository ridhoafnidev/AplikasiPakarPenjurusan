package com.example.feature.auth

import android.util.Log
import androidx.lifecycle.*
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.request.guru.RegisterGuruRequest
import com.example.core_data.api.request.siswa.RegisterSiswaRequest
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.domain.*
import com.example.core_data.repository.AuthRepository
import com.example.core_data.repository.LastResultRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val lastResultRepository: LastResultRepository
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

    var agamaId: String? = ""
    var agamaAyahId: String? = ""
    var agamaIbuId: String? = ""
    var tanggalLahir: String? = ""
    var kelas: String? = ""
    var pendidikanAyah: String? = ""
    var pendidikanIbu: String? = ""

    var username = ""
    var password = ""
    var idUser = ""
    var idUserSiswa = ""

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

    private val _lastResultAll = MutableLiveData<ApiEvent<LastResults?>>()
    val lastResultAll: LiveData<ApiEvent<LastResults?>> = _lastResultAll

    fun getLastResult(idUser: Int) {
        viewModelScope.launch {
            lastResultRepository.getLastResult(idUser, 0)
                .filterNot { it is ApiEvent.OnProgress }
                .collect { _lastResultAll.value = it }
        }
    }

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

    fun registerSiswa(
        level: String,
        usernames: String,
        passwords: String,
        nama: String,
        nisn: String,
        alamat: String,
        asalSekolah: String,
        statusAsalSekolah: String,
        namaAyah: String,
        pekerjaanAyah: String,
        namaIbu: String,
        umurIbu: String,
        pekerjaanIbu: String,
        tempatLahir: String
    ) {
        viewModelScope.launch {
            authRepository.registerSiswa(
                RegisterSiswaRequest(
                    level = level,
                    username = usernames,
                    password = passwords,
                    nama = nama,
                    nisn = nisn,
                    kelas = kelas.toString(),
                    tanggalLahir = tanggalLahir.toString(),
                    agama = agamaId.toString(),
                    alamat = alamat,
                    asalSekolah = asalSekolah,
                    statusAsalSekolah = statusAsalSekolah,
                    namaAyah = namaAyah,
                    agamaAyah = agamaAyahId.toString(),
                    pendidikanTerakhirAyah = pendidikanAyah.toString(),
                    pekerjaanAyah = pekerjaanAyah,
                    namaIbu = namaIbu,
                    umurIbu = umurIbu,
                    agamaIbu = agamaIbuId.toString(),
                    pendidikanTerakhirIbu = pendidikanIbu.toString(),
                    pekerjaanIbu = pekerjaanIbu,
                    tempatLahir = tempatLahir
                )
            )
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _registerSiswaRequest.value = it }
        }
    }

}