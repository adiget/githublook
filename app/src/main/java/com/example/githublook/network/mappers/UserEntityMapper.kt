package com.example.githublook.network.mappers

import com.example.githublook.data.model.UserEntity
import com.example.githublook.network.model.GithubUser
import javax.inject.Inject

class UserEntityMapper @Inject constructor() : EntityMapper<GithubUser, UserEntity> {

    override fun mapFromModel(model: GithubUser): UserEntity {
        return UserEntity(
            userName = model.login?:"",
            profilePic = model.avatarUrl?:""
        )
    }
}