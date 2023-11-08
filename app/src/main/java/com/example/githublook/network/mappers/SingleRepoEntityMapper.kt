package com.example.githublook.network.mappers

import com.example.githublook.network.model.GithubSingleRepo
import com.example.githublook.data.model.SingleRepoEntity
import javax.inject.Inject

class SingleRepoEntityMapper @Inject constructor() :
    EntityMapper<GithubSingleRepo, SingleRepoEntity> {

    override fun mapFromModel(model: GithubSingleRepo): SingleRepoEntity {
        return SingleRepoEntity(
            repoName = model.name?:"",
            repoDescription = model.description?:"",
            openIssuesCount = model.openIssuesCount?:0,
            forksCount = model.forksCount?:0,
            defaultBranch = model.defaultBranch?:""
        )
    }
}