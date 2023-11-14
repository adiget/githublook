package com.example.githublook.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.githublook.domain.GetPullRequestsUseCase
import com.example.githublook.domain.model.PullRequest
import com.example.githublook.domain.model.User
import com.example.githublook.presentation.mapper.PullRequestViewMapper
import com.example.githublook.presentation.mapper.UserViewMapper
import com.example.githublook.presentation.views.views.PullRequestView
import com.example.githublook.presentation.views.views.UserView
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PullRequestsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val useCase = mockk<GetPullRequestsUseCase>()
    private lateinit var viewModel: PullRequestsViewModel

    @Test
    fun userName_repoName_prState_matchesFromSavedStateHandle() {
        coEvery {
            useCase(
                GetPullRequestsUseCase.Params(
                    username = USER_NAME,
                    repoName = REPO_NAME,
                    state = PullRequest.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(testPrs)

        viewModel = PullRequestsViewModel(
            viewMapper = PullRequestViewMapper(UserViewMapper()),
            getPullRequestsUseCase = useCase,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    PullRequestsViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME,
                    PullRequestsViewModel.REPO_NAME_SAVED_STATE_KEY to REPO_NAME,
                    PullRequestsViewModel.PULL_REQUEST_STATE_STATE_KEY to REPO_STATE
                )
            )
        )

        assertEquals(USER_NAME, viewModel.userName)
        assertEquals(REPO_NAME, viewModel.repoName)
        assertEquals(REPO_STATE, viewModel.state)
    }

    @Test
    fun uiStatePrs_whenInitialized_thenShowLoading() = runTest {
        coEvery {
            useCase(
                GetPullRequestsUseCase.Params(
                    username = USER_NAME,
                    repoName = REPO_NAME,
                    state = PullRequest.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(testPrs)

        viewModel = PullRequestsViewModel(
            viewMapper = PullRequestViewMapper(UserViewMapper()),
            getPullRequestsUseCase = useCase,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    PullRequestsViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME,
                    PullRequestsViewModel.REPO_NAME_SAVED_STATE_KEY to REPO_NAME,
                    PullRequestsViewModel.PULL_REQUEST_STATE_STATE_KEY to REPO_STATE
                )
            )
        )

        assertEquals(PullRequestsViewModel.PrUiState.Loading, viewModel.prUiState.value)
    }

    @Test
    fun uiStatePrs_whenSuccess_matchesReposFromUseCase() = runTest {
        coEvery {
            useCase(
                GetPullRequestsUseCase.Params(
                    username = USER_NAME,
                    repoName = REPO_NAME,
                    state = PullRequest.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(testPrs)

        viewModel = PullRequestsViewModel(
            viewMapper = PullRequestViewMapper(UserViewMapper()),
            getPullRequestsUseCase = useCase,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    PullRequestsViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME,
                    PullRequestsViewModel.REPO_NAME_SAVED_STATE_KEY to REPO_NAME,
                    PullRequestsViewModel.PULL_REQUEST_STATE_STATE_KEY to REPO_STATE
                )
            )
        )

        viewModel.prUiState.test {
            assertEquals(PullRequestsViewModel.PrUiState.Success(testPrsForView), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun uiStatePrs_whenSuccessWithEmptyList_matchesReposFromUseCase() = runTest {
        coEvery {
            useCase(
                GetPullRequestsUseCase.Params(
                    username = USER_NAME,
                    repoName = REPO_NAME,
                    state = PullRequest.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(emptyList())

        viewModel = PullRequestsViewModel(
            viewMapper = PullRequestViewMapper(UserViewMapper()),
            getPullRequestsUseCase = useCase,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    PullRequestsViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME,
                    PullRequestsViewModel.REPO_NAME_SAVED_STATE_KEY to REPO_NAME,
                    PullRequestsViewModel.PULL_REQUEST_STATE_STATE_KEY to REPO_STATE
                )
            )
        )

        viewModel.prUiState.test {
            assertEquals(PullRequestsViewModel.PrUiState.Success(emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun uiStatePrs_whenError_shouldReturnError() = runTest {
            coEvery {
                useCase(
                    GetPullRequestsUseCase.Params(
                        username = USER_NAME,
                        repoName = REPO_NAME,
                        state = PullRequest.State.valueOf(REPO_STATE)
                    )
                )
            } returns flow{
                emit(listOf())
                throw Exception("Error retrieving pull requests")
            }

            viewModel = PullRequestsViewModel(
                viewMapper = PullRequestViewMapper(UserViewMapper()),
                getPullRequestsUseCase = useCase,
                savedStateHandle = SavedStateHandle(
                    mapOf(
                        PullRequestsViewModel.USER_NAME_SAVED_STATE_KEY to USER_NAME,
                        PullRequestsViewModel.REPO_NAME_SAVED_STATE_KEY to REPO_NAME,
                        PullRequestsViewModel.PULL_REQUEST_STATE_STATE_KEY to REPO_STATE
                    )
                )
            )

            viewModel.prUiState.test {
                assertEquals(PullRequestsViewModel.PrUiState.Error, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
}

private const val USER_NAME = "user1"
private const val REPO_NAME = "repo1"
private val REPO_STATE = "ALL"

private val testPrs = listOf(
    PullRequest(
        1,
        "pr1 desc",
        "pr1 title",
        User("user1"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    ),
    PullRequest(
        2,
        "pr2 desc",
        "pr2 title",
        User("user2"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    ),
    PullRequest(
        3,
        "pr3 desc",
        "pr3 title",
        User("user3"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    )
)

private val testPrsForView = listOf(
    PullRequestView(
        1,
        "pr1 title",
        "pr1 desc",
        UserView("user1"),
        "25 Oct'22",
        "25 Oct'22"
    ),
    PullRequestView(
        2,
        "pr2 title",
        "pr2 desc",
        UserView("user2"),
        "25 Oct'22",
        "25 Oct'22"
    ),
    PullRequestView(
        3,
        "pr3 title",
        "pr3 desc",
        UserView("user3"),
        "25 Oct'22",
        "25 Oct'22"
    )
)