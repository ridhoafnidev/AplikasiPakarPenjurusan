package com.kemenag_inhu.absensi.core_data.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kemenag_inhu.absensi.core_domain.model.User
import com.kemenag_inhu.absensi.core_domain.model.Users

@Entity
data class UserEntity(
    @PrimaryKey
    val idUser: Int,
    val nama: String,
    val nik: Int,
    val nip: Int,
    val noHp: String,
    val email: String,
    val levelId: Int,
    val isActive: Int,
    val loginRemark: String = "",
    val isCurrent: Boolean = false,
    )

typealias UserEntities = List<UserEntity>

//region Convert from Entity to Domain

internal fun UserEntity.toDomain() =
    User(idUser = idUser, nik = nik, nip = nip, levelId = levelId, isActive = isActive, nama = nama, email = email, noHp = noHp)


internal fun UserEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun User.toEntity() =
    UserEntity(idUser = idUser, nik = nik, nip = nip, levelId = levelId, isActive = isActive, nama = nama, email = email, noHp = noHp)

internal fun Users.toEntity() =
    map { it.toEntity() }

//endregion