package com.example.githublook.data.mapper

import com.example.githublook.data.model.PullRequestEntity
import com.example.githublook.data.model.UserEntity
import com.example.githublook.domain.model.PullRequest
import com.example.githublook.domain.model.User
import javax.inject.Inject

class PullRequestMapper @Inject constructor() : Mapper<PullRequestEntity, PullRequest> {

    override fun mapFromEntity(type: PullRequestEntity): PullRequest {
        return PullRequest(
            id = type.id,
            desc = type.desc,
            closedAt = type.closedAt,
            createdAt = type.createdAt,
            title = type.title,
            user = User(type.user.userName, type.user.profilePic)
        )
    }

    override fun mapToEntity(type: PullRequest): PullRequestEntity {
        return PullRequestEntity(
            id = type.id,
            desc = type.desc,
            closedAt = type.closedAt,
            createdAt = type.createdAt,
            title = type.title,
            user = UserEntity(type.user.userName, type.user.profilePic)
        )
    }
}