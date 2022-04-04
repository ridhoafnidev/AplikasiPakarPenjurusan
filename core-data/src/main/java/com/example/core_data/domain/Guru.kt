package com.example.core_data.domain

data class Guru(
    val idUser: Long = 0L,
    val username: String = "",
    val level: String = "",
    val lastLogin: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val nama: String = "",
    val nip: String = "",
    val alamat: String = "",
    val foto: String = "",
    val email: String = ""
)

typealias ListGuru = List<Guru>