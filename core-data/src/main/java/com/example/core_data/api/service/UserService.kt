package com.example.core_data.api.service

import com.example.core_data.api.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @FormUrlEncoded
    @POST(Login)
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : LoginResponse

    companion object {
        //region API Path
        const val Login = "login"
        //endregion
    }
}