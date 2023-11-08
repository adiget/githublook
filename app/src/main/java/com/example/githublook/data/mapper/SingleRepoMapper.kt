package com.example.githublook.data.mapper

import com.example.githublook.data.model.SingleRepoEntity
import com.example.githublook.domain.model.GitSingleRepo
import javax.inject.Inject

class SingleRepoMapper @Inject constructor() : Mapper<SingleRepoEntity, GitSingleRepo> {
    override fun mapFromEntity(type: SingleRepoEntity): GitSingleRepo {
        return GitSingleRepo(
            repoName = type.repoName,
            defaultBranch = type.defaultBranch,
            forksCount = type.forksCount,
            openIssuesCount = type.openIssuesCount,
            repoDescription = type.repoDescription
        )
    }

    override fun mapToEntity(type: GitSingleRepo): SingleRepoEntity {
        return SingleRepoEntity(
            repoName = type.repoName,
            defaultBranch = type.defaultBranch,
            forksCount = type.forksCount,
            openIssuesCount = type.openIssuesCount,
            repoDescription = type.repoDescription
        )
    }
}