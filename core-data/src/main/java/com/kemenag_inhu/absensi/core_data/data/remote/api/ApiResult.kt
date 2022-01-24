package com.kemenag_inhu.absensi.core_data.data.remote.api

sealed class ApiResult<out T> {
    data class OnSuccess<out T>(val response: T) :ApiResult<T>()
    data class OnFailed(val exception: ApiException) :ApiResult<Nothing>()
}