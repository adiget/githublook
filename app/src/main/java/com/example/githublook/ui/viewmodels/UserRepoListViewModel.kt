package com.example.githublook.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githublook.common.Result
import com.example.githublook.common.asResult
import com.example.githublook.domain.GetUserReposUseCase
import com.example.githublook.presentation.mapper.SingleGitRepoViewMapper
import com.example.githublook.presentation.views.views.SingleRepoView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserRepoListViewModel @Inject internal constructor(
    private val viewMapper: SingleGitRepoViewMapper,
    getUserReposUseCase: GetUserReposUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userName = savedStateHandle[USER_NAME_SAVED_STATE_KEY] ?: NO_USER_NAME

    val reposUiState: StateFlow<ReposUiState> = repositoriesUiState(
        userName = userName,
        reposViewMapper = viewMapper,
        useCase = getUserReposUseCase
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReposUiState.Loading,
    )

    private fun repositoriesUiState(
        userName: String,
        reposViewMapper: SingleGitRepoViewMapper,
        useCase: GetUserReposUseCase
    ): Flow<ReposUiState> = useCase(GetUserReposUseCase.Params(userName))
            .asResult()
            .map { reposResult ->
                when (reposResult) {
                    is Result.Success -> {
                        ReposUiState.Success(reposResult.data.map { reposViewMapper.mapToView(it) })
                    }

                    is Result.Loading -> {
                        ReposUiState.Loading
                    }

                    is Result.Error -> {
                        ReposUiState.Error
                    }
                }
            }

    sealed interface ReposUiState {
        data class Success(val repos: List<SingleRepoView>) : ReposUiState
        object Error : ReposUiState
        object Loading : ReposUiState
    }

    companion object {
        private const val NO_USER_NAME = ""
        private const val USER_NAME_SAVED_STATE_KEY = "userName"
    }
}