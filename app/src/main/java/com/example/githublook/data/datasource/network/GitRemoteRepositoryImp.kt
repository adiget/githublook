package com.example.githublook.data.datasource.network

import com.example.githublook.data.model.PullRequestEntity
import com.example.githublook.data.model.PullRequestGetBody
import com.example.githublook.data.model.SingleRepoEntity
import com.example.githublook.common.AppDispatchers
import com.example.githublook.common.Dispatcher
import com.example.githublook.network.GithubService
import com.example.githublook.network.mappers.PullRequestEntityMapper
import com.example.githublook.network.mappers.SingleRepoEntityMapper
import com.example.githublook.network.model.GithubPullRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GitRemoteRepositoryImp @Inject constructor(
    private val githubService: GithubService,
    private val singleRepoEntityMapper: SingleRepoEntityMapper,
    private val pullRequestEntityMapper: PullRequestEntityMapper,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GitRemote {

    override fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>> = flow {
        emit(githubService.getUserRepositories(username)
            .map { singleRepoEntityMapper.mapFromModel(it) })
    }.flowOn(ioDispatcher)

    override fun getPullRequestList(pullRequestGetBody: PullRequestGetBody): Flow<List<PullRequestEntity>> = flow {
        val pullRequestLists: List<GithubPullRequest> = githubService.getPullRequestForGithubRepo(
            pullRequestGetBody.userName,
            pullRequestGetBody.repositoryName,
            pullRequestGetBody.state
        )

        val pullRequestEntities: List<PullRequestEntity> = pullRequestLists.map {
            pullRequestEntityMapper.mapFromModel(it)
        }

        emit(pullRequestEntities)
    }.flowOn(ioDispatcher)
}