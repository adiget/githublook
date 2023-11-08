package com.example.githublook.data.datasource

import com.example.githublook.data.datasource.network.GitRemote
import com.example.githublook.data.model.PullRequestEntity
import com.example.githublook.data.model.PullRequestGetBody
import com.example.githublook.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitRemoteDataSource @Inject constructor(
    private val gitRemote: GitRemote
) : GitDataSource {

    override fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>> {
        return gitRemote.getUserGitRepositories(username)
    }

    override fun getPullRequestList(
        username: String,
        repoName: String,
        state: String
    ): Flow<List<PullRequestEntity>> {
        return gitRemote.getPullRequestList(PullRequestGetBody(username, repoName, state))
    }
}