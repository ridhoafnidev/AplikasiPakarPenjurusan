package com.kemenag_inhu.absensi.core_data.di

import com.kemenag_inhu.absensi.core_domain.usecase.*
import org.koin.dsl.module

val domainModule
    get() = module {
        factory<EventUseCase> { EventDbInteractor(get()) }
        factory<AuthUseCase> { AuthInteractor( get() ) }
//        factory<MasterJabatanStrukturalUseCase> { MasterJabatanStrukturalDbInteractor(get()) }
        //factory<UserUseCase> { UserDbInteractor(get()) }
//        factory<MasterJabatanFungsionalUseCase> { MasterJabatanFungsionalDbInteractor(get()) }
//        factory<MasterJenisTenagaUseCase> { MasterJenisTenagaDbInteractor(get()) }
//        factory<MasterLevelUseCase> { MasterLevelDbInteractor(get()) }
//        factory<MasterPangkatGolonganUseCase> { MasterPangkatGolonganDbInteractor(get()) }
//        factory<MasterPnsNonpnsUseCase> { MasterPnsNonpnsDbInteractor(get()) }
//        factory<MasterStatusAbsensiUseCase> { MasterStatusAbsensiDbInteractor(get()) }
//        factory<MasterUnitKerjaUseCase> { MasterUnitKerjaDbInteractor(get()) }
    }