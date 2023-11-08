package com.example.githublook.domain

import com.example.githublook.domain.model.PullRequest
import com.example.githublook.domain.model.GitSingleRepo
import kotlinx.coroutines.flow.Flow

interface GitRepository {

    fun getUserGitRepositories(username: String): Flow<List<GitSingleRepo>>

    fun getPullRequestList(
        username: String,
        repoName: String,
        state: PullRequest.State = PullRequest.State.ALL
    ): Flow<List<PullRequest>>
}