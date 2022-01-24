package com.kemenag_inhu.absensi.core_domain.usecase

import com.kemenag_inhu.absensi.core_domain.model.Auth
import com.kemenag_inhu.absensi.core_util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun login(auth: Auth) : Flow<Resource<Auth>>
}