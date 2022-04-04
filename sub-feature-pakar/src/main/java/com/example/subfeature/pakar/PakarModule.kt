package com.example.subfeature.pakar

import com.example.subfeature.pakar.fragments.eight.PakarEighthViewModel
import com.example.subfeature.pakar.fragments.five.PakarFifthViewModel
import com.example.subfeature.pakar.fragments.four.PakarFourthViewModel
import com.example.subfeature.pakar.fragments.nine.PakarNinethViewModel
import com.example.subfeature.pakar.fragments.one.PakarFirstViewModel
import com.example.subfeature.pakar.fragments.seven.PakarSeventhViewModel
import com.example.subfeature.pakar.fragments.three.PakarThirdViewModel
import com.example.subfeature.pakar.fragments.two.PakarSecondViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pakarModule
    get() = module {
        viewModel { PakarViewModel( get() ) }
        viewModel { PakarFirstViewModel() }
        viewModel { PakarSecondViewModel() }
        viewModel { PakarThirdViewModel() }
        viewModel { PakarFourthViewModel() }
        viewModel { PakarFifthViewModel() }
        viewModel { PakarSeventhViewModel() }
        viewModel { PakarEighthViewModel() }
        viewModel { PakarNinethViewModel(get(), get()) }
    }