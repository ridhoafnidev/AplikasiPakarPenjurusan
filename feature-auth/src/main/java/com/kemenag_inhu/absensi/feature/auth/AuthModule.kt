package com.kemenag_inhu.absensi.feature.auth

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule
    get() = module {
        viewModel { AuthViewModel(get()) }
    }