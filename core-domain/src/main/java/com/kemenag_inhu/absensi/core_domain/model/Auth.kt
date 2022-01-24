package com.kemenag_inhu.absensi.core_domain.model

data class Auth(
    val emailOrPhoneNumber: String,
    val password: String
)

val Auth.isSuccessLogin
    get() = emailOrPhoneNumber == EMAIL_OR_PHONE_NUMBER_LOGIN && password == PASSWORD_LOGIN

private const val EMAIL_OR_PHONE_NUMBER_LOGIN = "user"
private const val PASSWORD_LOGIN = "12345"


