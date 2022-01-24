package com.kemenag_inhu.absensi.core_data

import androidx.core.os.bundleOf
import com.kemenag_inhu.absensi.core.analytics.Analytics
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiException
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiResult
import java.lang.Exception

fun Analytics.logApiCallRequest(apiId: String){
    logMessage("calling API '$apiId'")
}

fun Analytics.logApiCallResponse(apiId: String, isSuccess: Boolean){
    logMessage("API call '$apiId' ${if (isSuccess) "completed successfully" else "failed" }")
}

fun Analytics.logApiResult(
    apiResult: ApiResult<*>,
    apiId: String,
    userId: Long,
    nikNip: Long
) {
    when(apiResult){
        is ApiResult.OnSuccess -> logEvent(
            name = "api_success",
            parameter = bundleOf("api_id" to apiId, "user_id" to userId, "nik_nip" to nikNip)
        )
        is ApiResult.OnFailed -> logApiException(apiResult.exception, apiId, userId, nikNip)
    }
}

fun Analytics.logApiException(
    apiException: ApiException,
    apiId: String,
    userId: Long,
    nikNip: Long
) {
    when(apiException) {
        ApiException.Unknown -> logEvent(
            name = "api_failed_unknown",
            parameter = bundleOf("api_id" to apiId, "user_id" to userId, "nik_nip" to nikNip)
        )
        ApiException.Offline -> logEvent(
            name = "api_failed_offline",
            parameter = bundleOf("api_id" to apiId, "user_id" to userId, "nik_nip" to nikNip)
        )
        ApiException.Timeout -> logEvent(
            name = "api_failed_timeout",
            parameter = bundleOf("api_id" to apiId, "user_id" to userId, "nik_nip" to nikNip)
        )
        ApiException.Network -> logEvent(
            name = "api_failed_network",
            parameter = bundleOf("api_id" to apiId, "user_id" to userId, "nik_nip" to nikNip)
        )
        ApiException.NullResponse -> logException(
            Exception("Null Response received from '$apiId' on 'user_id' = $userId and 'nik_nip' = $nikNip")
        )
        ApiException.EmptyResponse -> logException(
            Exception("Empty Response receice from '$apiId' on 'user_id' = $userId and 'nik_nip' = $nikNip")
        )
        is ApiException.Http -> logException(
            Exception("Empty Response receice from '$apiId' on 'user_id' = $userId and 'nik_nip' = $nikNip")
        )
        is ApiException.FailedResponse -> logException(
            Exception("Empty Response receice from '$apiId' on 'user_id' = $userId and 'nik_nip' = $nikNip")
        )
    }
}

fun Analytics.logSlowApiResponse(apiId: String, userId: Long, nikNip: Long, time: Long) {
    logEvent(
        name = "api_slow_response",
        parameter = bundleOf(
            "api_id" to apiId,
            "user_id" to userId,
            "nik_nip" to nikNip,
            "time" to time
        )
    )
}