package com.example.githublook.domain

import com.example.githublook.domain.model.GitSingleRepo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetUserReposUseCaseTest {
    private val repository = mockk<GitRepository>()
    val useCase = GetUserReposUseCase(repository)

    @Test
    fun when_repository_getUserGitRepositories_returns_valid_repolist_useCase_returns_same_list() = runTest {
        every { repository.getUserGitRepositories(USER_NAME) } returns flowOf(testRepos)

        val repositories: Flow<List<GitSingleRepo>> = useCase(GetUserReposUseCase.Params(USER_NAME))

        assertEquals(
            testRepos,
            repositories.first()
        )
    }

    @Test
    fun when_repository_getUserGitRepositories_returns_empty_list_useCase_returns_empty_list() = runTest {
        every { repository.getUserGitRepositories(USER_NAME) } returns flowOf(emptyList())

        val repositories: Flow<List<GitSingleRepo>> = useCase(GetUserReposUseCase.Params(USER_NAME))

        assertEquals(
            emptyList(),
            repositories.first()
        )
    }

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

    private companion object {
        const val USER_NAME = "userName"
    }
}