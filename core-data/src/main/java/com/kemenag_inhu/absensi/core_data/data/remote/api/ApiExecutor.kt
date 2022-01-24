package com.kemenag_inhu.absensi.core_data.data.remote.api

import com.kemenag_inhu.absensi.core.analytics.Analytics
import com.kemenag_inhu.absensi.core_data.logApiCallRequest
import kotlin.system.measureTimeMillis

class ApiExcecutor(
    private val analytics: Analytics,
    //private val userExtraDao: UserExtraDao
) {
    internal suspend fun <T> callApi(
        apiId: String = "",
        apiCall: suspend () -> T
    ) : ApiResult<T> {

        return runCatching {
            analytics.logApiCallRequest(apiId)
            var result: T?
            var responseTime = measureTimeMillis {
                result = apiCall.invoke()
            }

        }

    }
}