package com.example.githublook.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githublook.R
import com.example.githublook.presentation.views.views.PullRequestView
import com.example.githublook.presentation.views.views.UserView
import com.example.githublook.ui.theme.GithublookTheme
import com.example.githublook.ui.viewmodels.PullRequestsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullRequestsScreen(
    pullRequestsViewModel: PullRequestsViewModel = hiltViewModel(),
    onNavUp: () -> Unit,
    modifier: Modifier
) {
    val prUiState: PullRequestsViewModel.PrUiState by pullRequestsViewModel.prUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            GitHubAppTopAppBar(
                topAppBarText = stringResource(id = R.string.pull_requests),
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
                when(prUiState){
                    PullRequestsViewModel.PrUiState.Loading -> item {
                        Spacer(modifier = Modifier.height(100.dp))
                        Box(
                            modifier = Modifier.fillMaxSize()
                            .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressBar()
                        }
                    }

                    PullRequestsViewModel.PrUiState.Error -> {}

                    is PullRequestsViewModel.PrUiState.Success -> {
                        items((prUiState as PullRequestsViewModel.PrUiState.Success).prs){
                                pullRequest: PullRequestView -> PullRequestCard(
                            modifier = Modifier.padding(vertical = 8.dp),
                            pullRequest = pullRequest
                        )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PullRequestCard(
    pullRequest: PullRequestView,
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avtar(imageUrl = pullRequest.user.profilePic)

                Text(
                    text = pullRequest.prTitle,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = pullRequest.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                )
            }

            Row(
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(id = R.drawable.git_pull_request ),
                    contentDescription = "Image",
                    modifier = Modifier
                )

                Text(
                    text = pullRequest.closedAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = pullRequest.user.userName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
private fun Avtar(
    imageUrl: String,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f,
) {
    val assetId = if (lightTheme) {
        R.drawable.baseline_person_24
    } else {
        R.drawable.ic_github_logo_dark
    }

    CoilImage(
        imageUrl,
        assetId,
        R.string.user_profile_photo
    )
}

@Composable
fun CoilImage(
    imageUrl: String,
    defaultImageRes: Int,
    contentDescription: Int
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(defaultImageRes),
        contentDescription = stringResource(contentDescription),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
    )
}

@Preview
@Composable
fun PullRequestCardPreview(){
    GithublookTheme {
        PullRequestCard(
            PullRequestView(
                id = -1,
                prTitle = "Title",
                prDesc = "Description",
                user = UserView(
                    userName = "User Name"
                ),
                closedAt = "01 Aug 2023",
                createdAt = "25 Jul 2023"
            )
        )
    }
}

@Preview
@Composable
fun PullRequestsScreenPreview() {
    GithublookTheme {
        UserReposScreen(
            onNavUp = {},
            onCardClick = {},
            modifier = Modifier
        )
    }
}