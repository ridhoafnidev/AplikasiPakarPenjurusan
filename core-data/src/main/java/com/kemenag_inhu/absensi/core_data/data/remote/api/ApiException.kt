package com.kemenag_inhu.absensi.core_data.data.remote.api

sealed class ApiException {

    object Unknown : ApiException()
    object Offline : ApiException()
    object Timeout : ApiException()
    object Network : ApiException()
    object NullResponse : ApiException()
    object EmptyResponse : ApiException()

    data class Http(val httpCode: Int, val message: String) : ApiException() {
        @Suppress("unused")
        companion object {
            const val BAD_REQUEST  = 400
            const val CLIENT_ERROR = 422
            const val SERVER_ERROR = 500
        }
    }

    data class FailedResponse(val errorCode: String) : ApiException() {
        companion object {
            const val STATUS_FAIL = "Fail"
            const val MESSAGE_FAIL = "FAILED"
        }
    }
}

fun ApiException.toResult() = ApiResult.OnFailed(this)
fun <T> ApiException.toFailedEventNullable() = ApiEvent.OnFailed<T?>(this, null)
fun <T> ApiException.toFailedEventEmptyList() = ApiEvent.OnFailed<List<T>>(this, emptyList())