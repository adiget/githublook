package com.example.githublook.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githublook.common.Result
import com.example.githublook.common.asResult
import com.example.githublook.domain.GetPullRequestsUseCase
import com.example.githublook.domain.model.PullRequest
import com.example.githublook.presentation.mapper.PullRequestViewMapper
import com.example.githublook.presentation.views.views.PullRequestView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PullRequestsViewModel @Inject internal constructor(
    viewMapper: PullRequestViewMapper,
    getPullRequestsUseCase: GetPullRequestsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val userName = savedStateHandle[USER_NAME_SAVED_STATE_KEY] ?: NO_USER_NAME
    val repoName = savedStateHandle[REPO_NAME_SAVED_STATE_KEY] ?: NO_REPO_NAME
    val state = savedStateHandle[PULL_REQUEST_STATE_STATE_KEY] ?: NO_STATE

    val prUiState: StateFlow<PrUiState> = pullRequestsUiState(
        userName = userName,
        repoName = repoName,
        prState = state,
        prViewMapper = viewMapper,
        useCase = getPullRequestsUseCase
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PrUiState.Loading,
    )

    private fun pullRequestsUiState(
        userName: String,
        repoName: String,
        prState: String,
        prViewMapper: PullRequestViewMapper,
        useCase: GetPullRequestsUseCase
    ): Flow<PrUiState> = useCase(
        GetPullRequestsUseCase.Params(
            userName,
            repoName,
            PullRequest.State.valueOf(prState))
    )
        .asResult()
        .map { prsResult ->
            when (prsResult) {
                is Result.Success -> {
                    PrUiState.Success(prsResult.data.map { prViewMapper.mapToView(it) })
                }

                is Result.Loading -> {
                    PrUiState.Loading
                }

                is Result.Error -> {
                    PrUiState.Error
                }
            }
        }


    sealed interface PrUiState {
        data class Success(val prs: List<PullRequestView>) : PrUiState
        object Error : PrUiState
        object Loading : PrUiState
    }

      companion object {
         private const val NO_USER_NAME = ""
         private const val NO_REPO_NAME = ""
         private const val NO_STATE = ""
         const val USER_NAME_SAVED_STATE_KEY = "userName"
         const val REPO_NAME_SAVED_STATE_KEY = "repoName"
         const val PULL_REQUEST_STATE_STATE_KEY = "state"
      }
}