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
            /// This will always refer to the latest onTimeout function that
            // LandingScreen was recomposed with
            val currentOnTimeout by rememberUpdatedState(onTimeout)

            // Create an effect that matches the lifecycle of LandingScreen.
            // If LandingScreen recomposes or onTimeout changes,
            // the delay shouldn't start again.
            LaunchedEffect(Unit) {
                delay(SPLASH_WAIT_TIME)
                currentOnTimeout()
            }

            Image(
                painterResource(id = R.drawable.ic_github_logo_light),
                modifier = Modifier
                    // Set image size to 100 dp
                    .size(100.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape),
                contentDescription = null
            )

            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall
            )
        }
//        /// This will always refer to the latest onTimeout function that
//        // LandingScreen was recomposed with
//        val currentOnTimeout by rememberUpdatedState(onTimeout)
//
//        // Create an effect that matches the lifecycle of LandingScreen.
//        // If LandingScreen recomposes or onTimeout changes,
//        // the delay shouldn't start again.
//        LaunchedEffect(Unit) {
//            delay(SplashWaitTime)
//            currentOnTimeout()
//        }

//        Image(
//            painterResource(id = R.drawable.ic_github_logo),
//            modifier = Modifier
//                // Set image size to 100 dp
//                .size(100.dp)
//                // Clip image to be shaped as a circle
//                .clip(CircleShape),
//            contentDescription = null
//        )
//
//        // Add a vertical space between the author and message texts
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(text = "Hello")
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    GithublookTheme {
        LandingScreen({}, modifier = Modifier)
    }
}