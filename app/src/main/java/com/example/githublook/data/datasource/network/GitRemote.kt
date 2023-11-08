package com.example.githublook.data.datasource.network

import com.example.githublook.data.model.PullRequestEntity
import com.example.githublook.data.model.PullRequestGetBody
import com.example.githublook.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow

interface GitRemote {
    fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>>

    fun getPullRequestList(pullRequestGetBody: PullRequestGetBody): Flow<List<PullRequestEntity>>
}