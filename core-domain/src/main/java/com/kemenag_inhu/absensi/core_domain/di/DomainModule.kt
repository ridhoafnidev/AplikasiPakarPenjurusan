package com.kemenag_inhu.absensi.core_data.di

import com.kemenag_inhu.absensi.core_domain.usecase.AuthInteractor
import com.kemenag_inhu.absensi.core_domain.usecase.AuthUseCase
import com.kemenag_inhu.absensi.core_domain.usecase.EventDbInteractor
import com.kemenag_inhu.absensi.core_domain.usecase.EventUseCase
import org.koin.dsl.module

val domainModule
    get() = module {
        factory<EventUseCase> { EventDbInteractor(get()) }
        factory<AuthUseCase> { AuthInteractor( get() ) }
    }