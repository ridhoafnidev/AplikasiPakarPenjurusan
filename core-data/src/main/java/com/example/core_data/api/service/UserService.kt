package com.example.core_data.api.service

import com.example.core_data.api.response.LoginResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST(Login)
    suspend fun login(
        @Path("username") username: String,
        @Path("password") password: String
    ) : LoginResponse

    companion object {
        //region API Path

        const val Login = "login"

        //endregion
    }

}