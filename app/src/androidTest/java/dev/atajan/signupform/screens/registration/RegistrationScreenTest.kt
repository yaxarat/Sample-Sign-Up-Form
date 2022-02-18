package dev.atajan.signupform.screens.registration

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dev.atajan.signupform.common.ERROR_TEXT
import dev.atajan.signupform.common.HEADER_TEXT
import dev.atajan.signupform.common.PROGRESS_BUTTON
import dev.atajan.signupform.common.UserInput
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.RegistrationInputError.InvalidEmail
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.State
import dev.atajan.signupform.ui.theme.SignUpFormTheme
import org.junit.Rule
import org.junit.Test

class RegistrationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialStateTest() {
        composeTestRule.setContent {
            SignUpFormTheme {
                val state = State.initialState()

                AccountRegisterScreen(
                    screenState = state,
                    onIntention = {},
                    navigateToConfirmationScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(HEADER_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ERROR_TEXT).assertDoesNotExist()
        composeTestRule.onNodeWithTag(PROGRESS_BUTTON)
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun correctlyFilledStateTest() {
        val state = State(
            name = "yashar",
            email = UserInput(value = "email@mail.com", error = null),
            password = UserInput(value = "owdj(nsd2", error = null),
            website = UserInput(value = "", error = null)
        )

        composeTestRule.setContent {
            SignUpFormTheme {
                AccountRegisterScreen(
                    screenState = state,
                    onIntention = {},
                    navigateToConfirmationScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(HEADER_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ERROR_TEXT).assertDoesNotExist()
        composeTestRule.onNodeWithTag(PROGRESS_BUTTON)
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun incorrectlyFilledStateTest() {
        val state = State(
            name = "yashar",
            email = UserInput(value = "email", error = InvalidEmail),
            password = UserInput(value = "owdj(nsd2", error = null),
            website = UserInput(value = "", error = null)
        )

        composeTestRule.setContent {
            SignUpFormTheme {
                AccountRegisterScreen(
                    screenState = state,
                    onIntention = {},
                    navigateToConfirmationScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(HEADER_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ERROR_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(PROGRESS_BUTTON)
            .assertIsDisplayed()
            .assertIsEnabled()
    }
}