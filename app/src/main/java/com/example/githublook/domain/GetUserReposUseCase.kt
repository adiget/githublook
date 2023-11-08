package com.example.githublook.domain

import com.example.githublook.domain.model.GitSingleRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val gitRepository: GitRepository
) {
    operator fun invoke(requestValues: Params): Flow<List<GitSingleRepo>> =
        gitRepository.getUserGitRepositories(requestValues.username)

    data class Params(
        val username: String
    )
}