package com.example.subfeature.pakar

import com.example.subfeature.pakar.fragments.PakarFirstViewModel
import com.example.subfeature.pakar.fragments.PakarSecondViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pakarModule
    get() = module {
        viewModel { PakarViewModel() }
        viewModel { PakarFirstViewModel() }
        viewModel { PakarSecondViewModel() }
    }