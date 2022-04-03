package com.example.subfeature.nilai_siswa

import android.app.Application
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Application.nilaiSiswaModule
    get() = module {
        viewModel { NilaiSiswaViewModel(get()) }
    }