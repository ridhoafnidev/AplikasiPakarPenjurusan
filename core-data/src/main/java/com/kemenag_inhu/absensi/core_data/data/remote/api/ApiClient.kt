package com.kemenag_inhu.absensi.core_data.data.remote.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal inline fun <reified T> apiClient(
    baseUrl: String,
    apiClient: OkHttpClient
) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(apiClient)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(T::class.java)

internal fun httpClient(
    timeOut: Long,
    loggingLevel: HttpLoggingInterceptor.Level,
    vararg interceptors: Interceptor = emptyArray()
) :OkHttpClient =OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = loggingLevel
    })
    .apply {
        interceptors.forEach {
            addInterceptor(it)
        }
    }
    .callTimeout(timeOut, TimeUnit.SECONDS)
    .readTimeout(timeOut, TimeUnit.SECONDS)
    .connectTimeout(timeOut, TimeUnit.SECONDS)
    .writeTimeout(timeOut, TimeUnit.SECONDS)
    .build()