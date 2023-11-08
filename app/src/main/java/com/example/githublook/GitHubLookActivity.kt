package com.example.githublook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.githublook.ui.compose.GithubLookApp
import com.example.githublook.ui.theme.GithublookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubLookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GithublookTheme() {
                GithubLookApp()
            }
        }
    }
}