package com.kemenag_inhu.absensi.feature.home

import com.kemenag_inhu.absensi.feature.home.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule
    get() = module {
        viewModel { HomeViewModel(get()) }
        viewModel { ProfileViewModel(get(), get(), get()) }
    }