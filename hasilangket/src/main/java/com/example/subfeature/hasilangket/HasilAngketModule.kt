package com.example.subfeature.hasilangket

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val hasilAngketModule
    get() = module {
        viewModel { HasilAngketViewModel(get()) }
    }