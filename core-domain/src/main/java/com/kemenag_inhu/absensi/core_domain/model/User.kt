package com.kemenag_inhu.absensi.core_domain.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

data class User(
    val idUser: Int,
    val nik: Int,
    val nip: Int,
    val nama: String,
    val email: String,
    val noHp: String,
    val levelId: Int,
    val isActive: Int,
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
    idUser == other.idUser && nip == other.nip

val User.isEmploy
    get() = levelId == ROLE_PEGAWAI

val User.isAdmin
    get() = levelId == ROLE_ADMIN

private const val ROLE_PEGAWAI = 1
private const val ROLE_ADMIN = 2





