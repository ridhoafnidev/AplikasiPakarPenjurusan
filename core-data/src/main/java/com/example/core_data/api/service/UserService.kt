package com.example.core_data.api.service

import com.example.core_data.api.response.CommonResponse
import com.example.core_data.api.response.LoginResponse
import retrofit2.http.*

interface UserService {

    @FormUrlEncoded
    @POST(Login)
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @PUT(ChangePassword)
    suspend fun changePassword(
        @Path("id_user") idUser: Int,
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String,
    ) : CommonResponse

    companion object {
        //region API Path
        const val Login = "login"
        const val ChangePassword = "change-password/{id_user}"
        //endregion
    }
}