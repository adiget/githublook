package com.example.githublook.domain

import com.example.githublook.domain.model.PullRequest
import javax.inject.Inject

class GetPullRequestsUseCase @Inject constructor(
    private val gitRepository: GitRepository
){
    operator fun invoke(requestValues: Params) =
        gitRepository.getPullRequestList(
            requestValues.username,
            requestValues.repoName,
            requestValues.state
            )

    data class Params(
        val username: String,
        val repoName: String,
        val state: PullRequest.State
    )
}