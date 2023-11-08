package com.example.githublook.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githublook.domain.model.PullRequest

@Composable
fun GithubLookApp() {
    val navController = rememberNavController()
    GithubLookNavHost(
        navController = navController
    )
}

@Composable
fun GithubLookNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen(onTimeout = { navController.navigate(Screen.Welcome.route) })
        }
        composable( Screen.Welcome.route) {
            WelcomeScreen(
                onContinueClick = { navController.navigate(Screen.UserRepos.createRoute(it))},
                )
        }
        composable(
            Screen.UserRepos.route,
            arguments = listOf(navArgument("userName") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            UserReposScreen(
                onNavUp = navController::navigateUp,
                onCardClick = {
                    navController.navigate(
                        Screen.PullRequests.createRoute(
                            userName,
                            it,
                            PullRequest.State.ALL
                        )
                    ) },
                modifier = Modifier
            )
        }
        composable(
            Screen.PullRequests.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("repoName") { type = NavType.StringType },
                navArgument("state") { type = NavType.StringType }
            )
        ) {
            PullRequestsScreen(
                onNavUp = navController::navigateUp,
                modifier = Modifier
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Welcome : Screen("welcome")
    object UserRepos : Screen("userrepos/{userName}") {
        fun createRoute(userName: String) = "userrepos/$userName"
    }
    object PullRequests : Screen("pullrequests/{userName}/{repoName}/{state}") {
        fun createRoute(
            userName: String,
            repoName: String,
            state: PullRequest.State
            ) = "pullrequests/$userName/$repoName/${state.name}"
    }
}