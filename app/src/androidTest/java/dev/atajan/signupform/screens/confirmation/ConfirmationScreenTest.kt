package dev.atajan.signupform.screens.confirmation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import dev.atajan.signupform.common.HEADER_TEXT
import dev.atajan.signupform.common.LOADING_INDICATOR
import dev.atajan.signupform.common.PROGRESS_BUTTON
import dev.atajan.signupform.screens.confirmation.ConfirmationViewModel.State
import dev.atajan.signupform.ui.theme.SignUpFormTheme
import org.junit.Rule
import org.junit.Test

class ConfirmationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingIndicatorShown() {
        composeTestRule.setContent {
            SignUpFormTheme {
                val state = State.initialState()

                ConfirmationScreen(
                    screenState = state,
                    onIntention = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(LOADING_INDICATOR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HEADER_TEXT).assertDoesNotExist()
        composeTestRule.onNodeWithTag(PROGRESS_BUTTON).assertDoesNotExist()
    }

    @Test
    fun loadingIndicatorHidden() {
        composeTestRule.setContent {
            SignUpFormTheme {
                val state = State(
                    name = "",
                    email = "",
                    website = "",
                    isLoading = false,
                    showError = false
                )

                ConfirmationScreen(
                    screenState = state,
                    onIntention = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(LOADING_INDICATOR).assertDoesNotExist()
    }

    @Test
    fun allComponentsShown() {
        val state = State(
            name = "name",
            email = "email",
            website = "website",
            isLoading = false,
            showError = false
        )

        composeTestRule.setContent {
            SignUpFormTheme {
                ConfirmationScreen(
                    screenState = state,
                    onIntention = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(HEADER_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(PROGRESS_BUTTON).assertIsDisplayed()
        composeTestRule.onNodeWithText(state.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(state.email).assertIsDisplayed()
        composeTestRule.onNodeWithText(state.website).assertIsDisplayed()
    }
}