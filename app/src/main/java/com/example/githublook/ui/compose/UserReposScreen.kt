package com.example.githublook.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githublook.R
import com.example.githublook.presentation.views.views.SingleRepoView
import com.example.githublook.ui.theme.GithublookTheme
import com.example.githublook.ui.viewmodels.UserRepoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReposScreen(
    userReposViewModel: UserRepoListViewModel = hiltViewModel(),
    onNavUp: () -> Unit,
    onCardClick: (repoName: String) -> Unit,
    modifier: Modifier
) {
    val reposUiState: UserRepoListViewModel.ReposUiState by userReposViewModel.reposUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            GitHubAppTopAppBar(
                topAppBarText = stringResource(id = R.string.repositories),
                onNavUp = onNavUp,
            )
        },
        content = { contentPadding ->
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.safeDrawingPadding(),
                contentPadding = contentPadding
            ) {
                when(reposUiState){
                    UserRepoListViewModel.ReposUiState.Loading -> item {
                        Spacer(modifier = Modifier.height(100.dp))
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressBar()
                        }
                    }

                    UserRepoListViewModel.ReposUiState.Error -> {}

                    is UserRepoListViewModel.ReposUiState.Success -> {
                        items((reposUiState as UserRepoListViewModel.ReposUiState.Success).repos){
                            repository: SingleRepoView -> UserRepoCard(
                                modifier = Modifier.padding(vertical = 8.dp),
                                userRepo = repository,
                                onCardClick = onCardClick
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun UserRepoCard(
    userRepo: SingleRepoView,
    onCardClick: (repoName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable(
                onClick = { onCardClick(userRepo.repoName) }
            )
    ) {
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Text(
                text = userRepo.repoName,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userRepo.repoDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Image(
                    painterResource(id = R.drawable.ic_git_fork),
                    contentDescription = "Image",
                    modifier = Modifier
                )

                Text(text = userRepo.forksCount.toString())
            }
        }
    }
}

@Preview
@Composable
fun UserRepoCardPreview(){
    GithublookTheme {
        UserRepoCard(
            SingleRepoView(
                repoName = "Repository Name",
                repoDescription = "Repository Description",
                openIssuesCount = 0,
                forksCount = 0,
                defaultBranch = "Default Branch"
            ),
            onCardClick = {}
        )
    }
}

@Preview
@Composable
fun UserReposScreenPreview() {
    GithublookTheme {
        UserReposScreen(
            onNavUp = {},
            onCardClick = {},
            modifier = Modifier
        )
    }
}