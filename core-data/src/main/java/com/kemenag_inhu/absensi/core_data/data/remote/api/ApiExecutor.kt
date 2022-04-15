package com.kemenag_inhu.absensi.core_data.data.remote.api

import android.util.Log
import com.kemenag_inhu.absensi.core.analytics.Analytics
import com.kemenag_inhu.absensi.core_data.logApiCallRequest
import com.kemenag_inhu.absensi.core_data.logApiCallResponse
import com.kemenag_inhu.absensi.core_data.logApiResult
import com.kemenag_inhu.absensi.core_data.logSlowApiResponse
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.system.measureTimeMillis

class ApiExecutor(
    private val analytics: Analytics,
    //private val userExtraDao: UserExtraDao
) {
    suspend fun <T> callApi(
        apiId: String = "",
        apiCall: suspend () -> T
    ) : ApiResult<T> {

        //val currentUserId = getCurrentUserId()
        //val currentNikNip = getCurrentNikNip()

        val currentUserId = 0L
        val currentNikNip = 0L

        return runCatching {
            analytics.logApiCallRequest(apiId.key)
            var result: T?
            var responseTime = measureTimeMillis {
                result = apiCall.invoke()
            }
            analytics.logApiCallResponse(apiId.key, true)
            if (responseTime >= LOGGABLE_SLOW_RESPONSE){
                analytics.logSlowApiResponse(
                    apiId = apiId.key,
                    userId = currentUserId,
                    nikNip = currentNikNip,
                    time = responseTime
                )
            }
            ApiResult.OnSuccess(result!!)
        }.getOrElse {
            analytics.logApiCallResponse(apiId.key, false)

            ApiResult.OnFailed(
                when (it) {
                    is HttpException -> {
                        Log.e("error.http", "error -> HttpException")
                        it.toHttpException()
                    }
                    is SocketTimeoutException,
                    is InterruptedIOException -> {
                        Log.e("error.http", "error -> SocketTimeoutException")
                        ApiException.Timeout
                    }
                    is ConnectException -> {
                        Log.e("error.http", "error -> ConnectException")
                        ApiException.Offline
                    }
                    is IllegalArgumentException,
                    is JsonDataException,
                    is IOException -> {
                        Log.e("error.http", "error -> IOException")
                        ApiException.Network
                    }
                    else -> {
                        Log.e("error.http", "error -> UnknownException")
                        ApiException.Unknown
                    }
                }
            ).also {
                analytics.logApiResult(
                    apiResult = it,
                    apiId = apiId,
                    userId = currentUserId,
                    nikNip = currentNikNip
                )
            }
        }

    }

    suspend fun logException(apiId: String, apiException: ApiException) {
        Log.d("LogException", apiId)
        Log.d("LogException", apiException.toString())
//        analytics.logApiException(
//            apiException = apiException,
//            apiId = apiId.key,
//            orgId = getCurrentOrganization()?.id ?: 0L,
//            wardId = getCurrentWard()?.id ?: 0L
//        )
    }

    private fun HttpException.toHttpException(): ApiException.Http = try {
        response()?.errorBody()?.source()?.let {
            ApiException.Http(code(), message())
        } ?: ApiException.Http(code(), message())
    } catch (exception: Exception) {
        ApiException.Http(code(), message())
    }

    private inline val String.key: String
        get() = indexOf('/').let {
            if (it > 0) substring(0, it) else this
        }

}

private const val LOGGABLE_SLOW_RESPONSE = 3000