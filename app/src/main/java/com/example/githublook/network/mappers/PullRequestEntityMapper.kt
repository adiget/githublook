package com.example.githublook.network.mappers

import com.example.githublook.data.model.PullRequestEntity
import com.example.githublook.network.model.GithubPullRequest
import com.example.githublook.network.model.GithubUser
import javax.inject.Inject

class PullRequestEntityMapper @Inject constructor(private val userEntityMapper: UserEntityMapper) :
    EntityMapper<GithubPullRequest, PullRequestEntity> {

    override fun mapFromModel(model: GithubPullRequest): PullRequestEntity {
        return PullRequestEntity(
            id = model.id?:-1,
            desc = model.body?:"No description ",
            user = userEntityMapper.mapFromModel(model.user ?: GithubUser()),
            title = model.title ?:"No title",
            createdAt = model.createdAt ?:"",
            closedAt = model.closedAt ?:""
        )
    }
}