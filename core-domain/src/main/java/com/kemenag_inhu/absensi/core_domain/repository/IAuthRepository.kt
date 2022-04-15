package com.kemenag_inhu.absensi.core_domain.repository

import com.kemenag_inhu.absensi.core_domain.model.Auth
import com.kemenag_inhu.absensi.core_util.api.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun login(auth: Auth) : Flow<Resource<Auth>>
}