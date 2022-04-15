package com.kemenag_inhu.absensi.core_domain.usecase

import com.kemenag_inhu.absensi.core_domain.model.Auth
import com.kemenag_inhu.absensi.core_domain.repository.IAuthRepository
import com.kemenag_inhu.absensi.core_util.api.Resource
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepository: IAuthRepository) : AuthUseCase {
    override fun login(auth: Auth): Flow<Resource<Auth>> = authRepository.login(auth)
}