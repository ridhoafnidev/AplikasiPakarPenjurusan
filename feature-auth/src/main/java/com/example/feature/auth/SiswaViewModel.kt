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
import com.example.core_data.api.request.siswa.UpdateSiswaRequest
import com.example.core_data.domain.ListSiswa
import com.example.core_data.domain.Siswa
import com.example.core_data.repository.SiswaRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SiswaViewModel(
    private val siswaRepository: SiswaRepository
) : ViewModel() {

    var agamaId: String? = ""
    var agamaAyahId: String? = ""
    var agamaIbuId: String? = ""
    var tanggalLahir: String? = ""
    var kelas: String? = ""
    var pendidikanAyah: String? = ""
    var pendidikanIbu: String? = ""

    private val _uploadFotoSiswa = MutableLiveData<ApiEvent<Siswa?>>()
    val uploadFotoSiswa: LiveData<ApiEvent<Siswa?>> = _uploadFotoSiswa

    private val _updateSiswa = MutableLiveData<ApiEvent<Siswa?>>()
    val updateSiswa: LiveData<ApiEvent<Siswa?>> = _updateSiswa

    private val _siswaGetAll = MutableLiveData<ApiEvent<ListSiswa?>>()
    val siswaGetAll: LiveData<ApiEvent<ListSiswa?>> = _siswaGetAll

    @RequiresApi(Build.VERSION_CODES.Q)
    fun updateFotoSiswa(
        id: Int,
        filePath: String,
        uri: Uri,
        contentResolver: ContentResolver,
        context: Context
    ) {
        viewModelScope.launch {
            siswaRepository.updateFotoSiswa(id, filePath, uri, contentResolver, context)
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _uploadFotoSiswa.value = it }
        }
    }

    fun updateSiswa(
        idUser: Int,
        nama: String,
        nisn: String,
        alamat: String,
        asalSekolah: String,
        statusAsalSekolah: String,
        namaAyah: String,
        umurAyah: String,
        pekerjaanAyah: String,
        namaIbu: String,
        umurIbu: String,
        pekerjaanIbu: String,
        tempatLahir: String
    ) {
        viewModelScope.launch {
            siswaRepository.updateSiswa(
                idUser = idUser, updateSiswaRequest = UpdateSiswaRequest(
                    nama = nama,
                    nisn = nisn,
                    kelas = kelas.toString(),
                    tanggalLahir = tanggalLahir.toString(),
                    agama = agamaId.toString(),
                    alamat = alamat,
                    asalSekolah = asalSekolah,
                    statusAsalSekolah = statusAsalSekolah,
                    namaAyah = namaAyah,
                    umurAyah = umurAyah,
                    agamaAyah = agamaAyahId.toString(),
                    pendidikanTerakhirAyah = pendidikanAyah.toString(),
                    pekerjaanAyah = pekerjaanAyah,
                    namaIbu = namaIbu,
                    umurIbu = umurIbu,
                    agamaIbu = agamaIbuId.toString(),
                    pendidikanTerakhirIbu = pendidikanIbu.toString(),
                    pekerjaanIbu = pekerjaanIbu,
                    tempatLahir = tempatLahir,
                )
            )
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _updateSiswa.value = it }
        }
    }

    fun getSiswaAll() {
        viewModelScope.launch {
            siswaRepository.getSiswaAll()
                .onStart { emit(ApiEvent.OnProgress()) }
                .collect { _siswaGetAll.value = it }
        }
    }
}