package com.kemenag_inhu.absensi.feature.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule
    get() = module {
        viewModel { HomeViewModel(get()) }
    }