package com.example.subfeature.nilai_penjurusan

import android.app.Application
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Application.nilaiPenjurusanModule
    get() = module {
        viewModel { NilaiPenjurusanViewModel(get(), get()) }
    }