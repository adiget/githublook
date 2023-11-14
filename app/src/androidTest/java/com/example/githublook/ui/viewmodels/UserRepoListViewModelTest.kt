package com.example.githublook.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.githublook.domain.GetUserReposUseCase
import com.example.githublook.domain.model.GitSingleRepo
import com.example.githublook.presentation.mapper.SingleGitRepoViewMapper
import com.example.githublook.presentation.views.views.SingleRepoView
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class UserRepoListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val useCase = mockk<GetUserReposUseCase>()
    private lateinit var viewModel: UserRepoListViewModel

    @Test
    fun userName_matchesUserNameFromSavedStateHandle() {
        coEvery { useCase(GetUserReposUseCase.Params(username = USER_NAME)) } returns flowOf(testRepos)

        viewModel = UserRepoListViewModel(
            viewMapper = SingleGitRepoViewMapper(),
            getUserReposUseCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(UserRepoListViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME))
        )

        assertEquals(USER_NAME, viewModel.userName)
    }

    @Test
    fun uiStateRepos_whenInitialized_thenShowLoading() = runTest {
        coEvery { useCase(GetUserReposUseCase.Params(username = USER_NAME)) } returns flowOf(testRepos)

        viewModel = UserRepoListViewModel(
            viewMapper = SingleGitRepoViewMapper(),
            getUserReposUseCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(UserRepoListViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME))
        )

        assertEquals(UserRepoListViewModel.ReposUiState.Loading, viewModel.reposUiState.value)
    }

    @Test
    fun uiStateRepos_whenSuccess_matchesReposFromUseCase() = runTest {
        coEvery { useCase(GetUserReposUseCase.Params(username = USER_NAME)) } returns flowOf(testRepos)

        viewModel = UserRepoListViewModel(
            viewMapper = SingleGitRepoViewMapper(),
            getUserReposUseCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(UserRepoListViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME))
        )

        viewModel.reposUiState.test {
            assertEquals(UserRepoListViewModel.ReposUiState.Success(testReposForView), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun uiStateRepos_whenSuccessWithEmptyList_matchesReposFromUseCase() = runTest {
        coEvery { useCase(GetUserReposUseCase.Params(username = USER_NAME)) } returns flowOf(
            emptyList()
        )

        viewModel = UserRepoListViewModel(
            viewMapper = SingleGitRepoViewMapper(),
            getUserReposUseCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(UserRepoListViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME))
        )

        viewModel.reposUiState.test {
            assertEquals(UserRepoListViewModel.ReposUiState.Success(emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun uiStateRepos_whenError_shouldReturnError() {
        runTest {
            coEvery { useCase(GetUserReposUseCase.Params(username = USER_NAME)) } returns flow {
                emit(listOf())
                throw Exception("Error retrieving repos")
            }

            viewModel = UserRepoListViewModel(
                viewMapper = SingleGitRepoViewMapper(),
                getUserReposUseCase = useCase,
                savedStateHandle = SavedStateHandle(mapOf(UserRepoListViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME))
            )

            viewModel.reposUiState.test {
                assertEquals(UserRepoListViewModel.ReposUiState.Error, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}

private const val USER_NAME = "user1"

private val testRepos = listOf(
    GitSingleRepo(
        "repo1",
        "repo1 desc",
        1,
        1,
        "branch1"
    ),
    GitSingleRepo(
        "repo2",
        "repo2 desc",
        2,
        2,
        "branch2"
    ),
    GitSingleRepo(
        "repo3",
        "repo3 desc",
        3,
        3,
        "branch3"
    )
)

private val testReposForView = listOf(
    SingleRepoView(
        repoName = "repo1",
        repoDescription = "repo1 desc",
        forksCount = 1
    ),
    SingleRepoView(
        repoName = "repo2",
        repoDescription = "repo2 desc",
        forksCount = 2
    ),
    SingleRepoView(
        repoName = "repo3",
        repoDescription = "repo3 desc",
        forksCount = 3
    )
)