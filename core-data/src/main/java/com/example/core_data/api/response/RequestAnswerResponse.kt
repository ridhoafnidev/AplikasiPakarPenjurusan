package com.example.core_data.api.response

import com.example.core_data.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestAnswerResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: LoginDataResponse
)

//@JsonClass(generateAdapter = true)
//data class LoginDataResponse(
//    @Json(name = "id_user")
//    val idUser: Long = 0L,
//    @Json(name = "username")
//    val username: String = "",
//    @Json(name = "nama")
//    val nama: String = "",
//    @Json(name = "level")
//    val level: String = "",
//    @Json(name = "last_login")
//    val lastLogin: String = ""
//)

//region Convert from Response to Domain

//internal fun LoginDataResponse.toDomain() =
//    User(idUser = idUser, username = username, level = level, lastLogin = lastLogin, nama = nama, isCurrent = false)


//endregion
