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
import com.example.core_data.api.request.guru.UpdateGuruRequest
import com.example.core_data.api.response.guru.toDomain
import com.example.core_data.api.service.GuruService
import com.example.core_data.api.toFailedEvent
import com.example.core_data.domain.Guru
import com.example.core_data.persistence.dao.GuruDao
import com.example.core_data.persistence.entity.toEntity
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class GuruRepository internal constructor(
    private val apiExecutor: ApiExecutor,
    private val guruService: GuruService,
    private val guruDao: GuruDao,
    private val jsonParser: Moshi,
) {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun updateFotoGuru(
        idUser: Int,
        filePath: String,
        imageUri: Uri,
        contentResolver: ContentResolver,
        context: Context
    ): Flow<ApiEvent<Guru?>> = flow {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(imageUri, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val imageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()

        val file = File(imageDir, filePath)
        val outStream = FileOutputStream(file)
        inputStream.copyTo(outStream)

        val body = UploadRequestBody(file, "image")

        runCatching {
            val apiId = GuruService.UpdateFoto
            val apiResult = apiExecutor.callApi(apiId) {
                guruService.updatePhotoGuru(
                    idUser = idUser,
                    foto = MultipartBody.Part.createFormData(
                        "foto",
                        file.name,
                        body
                    )
                )
            }

            val apiEvent: ApiEvent<Guru?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        guruDao.replaceById(idUser, this.toEntity())
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }
            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<Guru>())
        }
    }

    fun updateGuru(
        idUser: Int,
        updateGuruRequest: UpdateGuruRequest
    ): Flow<ApiEvent<Guru?>> = flow {
        runCatching {
            val apiId = GuruService.UpdateGuru

            val apiResult = apiExecutor.callApi(apiId) {
                guruService.updateGuru(idUser = idUser, updateGuruRequest)
            }

            val apiEvent: ApiEvent<Guru?> = when (apiResult) {
                is ApiResult.OnFailed -> apiResult.exception.toFailedEvent()
                is ApiResult.OnSuccess -> with(apiResult.response.data) {
                    toDomain().run {
                        guruDao.replaceById(idUser, this.toEntity())
                        ApiEvent.OnSuccess.fromServer(this)
                    }
                }
            }

            emit(apiEvent)
        }.onFailure {
            emit(it.toFailedEvent<Guru?>())
        }
    }
}