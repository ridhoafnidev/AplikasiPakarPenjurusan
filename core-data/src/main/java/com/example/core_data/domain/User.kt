package com.example.core_data.domain

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class User(
    val idUser: Long,
    val idSiswa: Long = 0,
    val idGuru: Long = 0,
    val username: String,
    val nama: String,
    val level: String,
    val lastLogin: String = "",
    val loginRemark: String = "",
    val isCurrent: Boolean = false
){
    inline val asJson: String
        get() = jsonParser.toJson(this)

    companion object {
        inline val jsonParser: JsonAdapter<User>
            get() = Moshi.Builder()
                .build()
                .adapter(User::class.java)

        fun fromJson(userJson: String): User? =
            jsonParser.fromJson(userJson)
    }
}

typealias Users = List<User>

infix fun User.sameWith(other: User) =
    idUser == other.idUser && username == other.username

val User.isSiswa
    get() = level == ROLE_SISWA

val User.isGuru
    get() = level == ROLE_GURU

private const val ROLE_SISWA = "siswa"
private const val ROLE_GURU = "guru"





