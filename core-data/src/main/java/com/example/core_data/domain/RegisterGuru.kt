package com.example.core_data.domain

data class RegisterGuru(
    val idUser: Long = 0L,
    val userId: Long = 0L,
    val nama: String = "",
    val username: String = "",
    val nip: String = "",
    val alamat: String = "",
    val foto: String = "",
    val email: String = "",
    val updatedAt: String = "",
    val createdAt: String = ""
)