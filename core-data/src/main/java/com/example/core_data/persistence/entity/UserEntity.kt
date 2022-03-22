package com.example.core_data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core_data.domain.User
import com.example.core_data.domain.Users

@Entity
data class UserEntity(
    @PrimaryKey
    val idUser: Long,
    val username: String,
    val nama: String,
    val level: String,
    val lastLogin: String,
    val loginRemark: String = "",
    val isCurrent: Boolean,
    )

typealias UserEntities = List<UserEntity>

//region Convert from Entity to Domain

internal fun UserEntity.toDomain() =
    User(
       idUser = idUser, username = username, nama = nama, level = level, lastLogin = lastLogin, loginRemark = loginRemark, isCurrent = isCurrent
    )

internal fun UserEntities.toDomain() =
    map { it.toDomain() }

//endregion
//region Convert from Domain to Entity

internal fun User.toEntity() =
    UserEntity(idUser = idUser, username = username, nama = nama, level = level, lastLogin = lastLogin, loginRemark = loginRemark, isCurrent = isCurrent)

internal fun Users.toEntity() =
    map { it.toEntity() }

//endregion