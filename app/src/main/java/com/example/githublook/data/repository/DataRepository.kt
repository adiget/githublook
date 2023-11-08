package com.example.githublook.data.repository

import com.example.githublook.common.AppDispatchers
import com.example.githublook.data.datasource.GitDataSource
import com.example.githublook.data.datasource.GitDataSourceFactory
import com.example.githublook.data.mapper.PullRequestMapper
import com.example.githublook.data.mapper.SingleRepoMapper
import com.example.githublook.data.model.PullRequestEntity
import com.example.githublook.common.Dispatcher
import com.example.githublook.domain.GitRepository
import com.example.githublook.domain.model.GitSingleRepo
import com.example.githublook.domain.model.PullRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val singleRepoMapper: SingleRepoMapper,
    private val pullRequestMapper: PullRequestMapper,
    private val gitDataSourceFactory: GitDataSourceFactory,
    @Dispatcher(AppDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher
) : GitRepository {

    override fun getUserGitRepositories(username: String): Flow<List<GitSingleRepo>> =
        gitDataSourceFactory.getRemoteDataSource()
            .getUserGitRepositories(username).map { it ->
                it.map { singleRepoMapper.mapFromEntity(it)}
            }.flowOn(defaultDispatcher)

     override fun getPullRequestList(
         username: String,
         repoName: String,
         state: PullRequest.State
     ): Flow<List<PullRequest>> {
        val gitDataSource: GitDataSource = gitDataSourceFactory.getRemoteDataSource()

         val pullRequestEntityListFlow : Flow<List<PullRequestEntity>> = gitDataSource.getPullRequestList(
             username,
             repoName,
             getPullRequestState(state)
         )

         val pullRequestListFlow: Flow<List<PullRequest>> = pullRequestEntityListFlow.map { 
             it -> it.map { pullRequestMapper.mapFromEntity(it) }
         }
         
         return pullRequestListFlow.flowOn(defaultDispatcher)
    }

    private fun getPullRequestState(state: PullRequest.State): String {
        return when (state) {
            PullRequest.State.OPEN -> "open"
            PullRequest.State.CLOSED -> "closed"
            else -> "all"
        }
    }
}