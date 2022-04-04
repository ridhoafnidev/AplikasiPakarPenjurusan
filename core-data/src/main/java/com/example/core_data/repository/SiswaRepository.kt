package com.example.core_data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.example.core_data.UploadRequestBody
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.ApiExecutor
import com.example.core_data.api.ApiResult
import com.example.core_data.api.request.siswa.UpdateSiswaRequest
import com.example.core_data.api.response.siswa.toDomain
import com.example.core_data.api.service.SiswaService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.ListSiswa
import com.example.core_data.domain.Siswa
import com.example.core_data.persistence.dao.SiswaDao
import com.example.core_data.persistence.entity.toEntity
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SiswaRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val siswaService: SiswaService,
    private val siswaDao: SiswaDao,
    private val jsonParser: Moshi,
) {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun updateFotoSiswa(
        idUser: Int,
        filePath: String,
        imageUri: Uri,
        contentResolver: ContentResolver,
        context: Context
    ): Flow<ApiEvent<Siswa?>> = flow {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(imageUri, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val imageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val file = File(imageDir, filePath)
        val outStream = FileOutputStream(file)
        inputStream.copyTo(outStream)

        val body = UploadRequestBody(file, "image")

        runCatching {
            val apiId = SiswaService.UpdateFoto
            val apiResult = apiExecutor.callApi(apiId) {
                siswaService.updatePhotoSiswa(
                    idUser = idUser,
                    foto = MultipartBody.Part.createFormData(
                        "foto",
                        file.name,
                        body
                    )
                )
            }

            val apiEvent: ApiEvent<Siswa?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        siswaDao.replaceById(idUser, this.toEntity())
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<Siswa>())
        }
    }

    fun updateSiswa(
        idUser: Int,
        updateSiswaRequest: UpdateSiswaRequest
    ): Flow<ApiEvent<Siswa?>> = flow {
        runCatching {
            val apiId = SiswaService.UpdateSiswa

            val apiResult = apiExecutor.callApi(apiId) {
                siswaService.updateSiswa(idUser = idUser, updateSiswaRequest)
            }

            val apiEvent: ApiEvent<Siswa?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        siswaDao.replaceById(idUser, this.toEntity())
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }

            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<Siswa?>())
        }
    }

    fun getSiswaAll(): Flow<ApiEvent<ListSiswa?>> = flow {
        runCatching {
            val apiId = SiswaService.GetAllSiswa

            val apiResult = apiExecutor.callApi(apiId) {
                siswaService.getAllSiswa()
            }

            val apiEvent: ApiEvent<ListSiswa?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        if (isEmpty()) {
                            ApiEvent.OnSuccess.fromServer(emptyList())
                        } else {
                            ApiEvent.OnSuccess.fromServer(this)
                        }
                    }
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<ListSiswa>())
        }
    }
}