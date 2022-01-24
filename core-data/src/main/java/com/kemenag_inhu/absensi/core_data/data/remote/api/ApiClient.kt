package com.kemenag_inhu.absensi.core_resource.components.api

internal inline fun <reified T> apiClient(
    baseUrl: String,
    apiClient: OkHttpClient
)