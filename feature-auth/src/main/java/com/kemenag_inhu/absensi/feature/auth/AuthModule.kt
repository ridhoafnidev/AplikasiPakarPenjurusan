package com.kemenag_inhu.absensi.feature.auth

import com.kemenag_inhu.absensi.feature.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule
    get() = module {
        viewModel { AuthViewModel(get()) }
        viewModel { LoginViewModel(get(), get(), get(), get()) }
        //viewModel { LoginViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    }