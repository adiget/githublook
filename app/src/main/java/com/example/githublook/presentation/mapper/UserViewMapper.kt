package com.example.githublook.presentation.mapper

import com.example.githublook.domain.model.User
import com.example.githublook.presentation.views.views.UserView
import javax.inject.Inject

class UserViewMapper @Inject constructor() : Mapper<UserView, User> {
    override fun mapToView(type: User): UserView {
        return UserView(
            userName = type.userName,
            profilePic = type.profilePic
        )
    }
}