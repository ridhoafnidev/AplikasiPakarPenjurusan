package com.kemenag_inhu.absensi.core_data.data

import com.kemenag_inhu.absensi.core_domain.model.Auth
import com.kemenag_inhu.absensi.core_domain.model.isSuccessLogin
import com.kemenag_inhu.absensi.core_domain.repository.IAuthRepository
import com.kemenag_inhu.absensi.core_util.api.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository : IAuthRepository {
    override fun login(auth: Auth): Flow<Resource<Auth>> = flow {
        emit(Resource.Loading())
        if (auth.isSuccessLogin){
            emit(Resource.Success(auth))
        }
        else{
            emit(Resource.Error<Auth>("Email/Nomor Handphone atau Password salah"))
        }
    }
}