package com.example.githublook.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githublook.R
import com.example.githublook.ui.theme.GithublookTheme
import kotlinx.coroutines.delay

private const val SPLASH_WAIT_TIME: Long = 2000

@Composable
fun LandingScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            val currentOnTimeout by rememberUpdatedState(onTimeout)

            LaunchedEffect(Unit) {
                delay(SPLASH_WAIT_TIME)
                currentOnTimeout()
            }

            Image(
                painterResource(id = R.drawable.ic_github_logo_light),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    GithublookTheme {
        LandingScreen({}, modifier = Modifier)
    }
}