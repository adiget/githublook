package com.example.githublook.data.datasource

import com.example.githublook.data.model.PullRequestEntity
import com.example.githublook.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow

interface GithubLookDataSource {
    fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>>

    fun getPullRequestList(
        username: String,
        repoName: String,
        state: String
    ): Flow<List<PullRequestEntity>>
}