package com.kemenag_inhu.absensi.subfeature.create_event

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createEventModule
    get() = module {
        viewModel { CreateEventViewModel(get()) }
    }