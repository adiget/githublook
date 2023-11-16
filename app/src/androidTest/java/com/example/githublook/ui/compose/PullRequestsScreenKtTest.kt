package com.example.githublook.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githublook.presentation.views.views.PullRequestView
import com.example.githublook.presentation.views.views.UserView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PullRequestsScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun prList_itemShown() {
        startPrList()
        composeTestRule.onNodeWithText("pr title").assertIsDisplayed()
    }

    private fun startPrList() {
        composeTestRule.setContent {
            PullRequestsScreen(
                pullRequests = listOf(prForTesting()),
                contentPadding = PaddingValues(all = 8.dp),
                modifier = Modifier
            )
        }
    }

    @Test
    fun prCard_itemShown() {
        composeTestRule.setContent {
            PullRequestCard(
                pullRequest = prForTesting()
            )
        }

        composeTestRule.onNodeWithText("pr title").assertIsDisplayed()
        composeTestRule.onNodeWithText("user name").assertIsDisplayed()
        composeTestRule.onNodeWithText("pr desc").assertDoesNotExist()
    }
}

@Composable
internal fun prForTesting() =
    PullRequestView(
        1,
        "pr title",
        "pr desc",
        UserView("user name")
    )