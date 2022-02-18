package dev.atajan.signupform.flows

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dev.atajan.interactor.UserProfileUseCases
import dev.atajan.live.database.UserDatabase
import dev.atajan.signupform.MainActivity
import dev.atajan.signupform.common.EMAIL_FIELD
import dev.atajan.signupform.common.HEADER_TEXT
import dev.atajan.signupform.common.NAME_FIELD
import dev.atajan.signupform.common.PASSWORD_FIELD
import dev.atajan.signupform.common.PROGRESS_BUTTON
import dev.atajan.signupform.common.USER_DETAIL_EMAIL
import dev.atajan.signupform.common.USER_DETAIL_NAME
import dev.atajan.signupform.common.USER_DETAIL_WEBSITE
import dev.atajan.signupform.common.WEBSITE_FIELD
import dev.atajan.signupform.di.InteractorModule
import dev.atajan.signupform.navigation.Screen
import dev.atajan.signupform.screens.confirmation.ConfirmationScreen
import dev.atajan.signupform.screens.confirmation.ConfirmationViewModel
import dev.atajan.signupform.screens.registration.AccountRegisterScreen
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel
import dev.atajan.signupform.ui.theme.SignUpFormTheme
import dev.atajan.test.FakeUserDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@UninstallModules(InteractorModule::class)
@HiltAndroidTest
class RegistrationToConfirmationFlowTest {

    /**
     * Test dependency injection set up
     */
    @Module
    @InstallIn(SingletonComponent::class)
    object TestGraph {
        @Provides
        @Singleton
        fun provideFakeDatabase() : UserDatabase {
            return FakeUserDatabase()
        }

        @Provides
        @Singleton
        fun provideFakeUseCase(database: UserDatabase) : UserProfileUseCases {
            return UserProfileUseCases.buildFake(database)
        }
    }

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val androidComposeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        androidComposeRule.setContent {
            SignUpFormTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.AccountRegistration.route
                ) {
                    /**
                     * Registration screen
                     */
                    composable(Screen.AccountRegistration.route) {
                        val accountRegistrationViewModel: AccountRegistrationViewModel = hiltViewModel()
                        AccountRegisterScreen(
                            screenState = accountRegistrationViewModel.state.value,
                            onIntention = accountRegistrationViewModel::onIntention,
                            navigateToConfirmationScreen = { email ->
                                navController.navigate(route = Screen.Confirmation.route + "/$email") {
                                    popUpTo(Screen.AccountRegistration.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }

                    /**
                     * Confirmation Screen
                     */
                    composable(
                        route = Screen.Confirmation.route + "/{email}",
                        arguments = Screen.Confirmation.arguments
                    ) {
                        val accountRegistrationViewModel: ConfirmationViewModel = hiltViewModel()
                        ConfirmationScreen(screenState = accountRegistrationViewModel.state.value) {}
                    }
                }
            }
        }
    }

    /**
     * Happy path - Registration -> Confirmation
     */

    @Test
    fun successRegistrationToConfirmation() {
        // Fill in details
        androidComposeRule.onNodeWithTag(NAME_FIELD).performTextInput("Audry")
        androidComposeRule.onNodeWithTag(EMAIL_FIELD).performTextInput("audry@outlook.com")
        androidComposeRule.onNodeWithTag(PASSWORD_FIELD).performTextInput("STRONG_PASS")
        androidComposeRule.onNodeWithTag(WEBSITE_FIELD).performTextInput("https://info.flipgrid.com")

        // Tap submit
        androidComposeRule.onNodeWithTag(PROGRESS_BUTTON).performClick()

        // Confirm submitted
        androidComposeRule.onNodeWithTag(HEADER_TEXT, useUnmergedTree = true).assertIsDisplayed()
        androidComposeRule.onNodeWithTag(USER_DETAIL_EMAIL, useUnmergedTree = true).assertTextContains("audry@outlook.com")
        androidComposeRule.onNodeWithTag(USER_DETAIL_NAME, useUnmergedTree = true).assertTextContains("Audry")
        androidComposeRule.onNodeWithTag(USER_DETAIL_WEBSITE, useUnmergedTree = true).assertTextContains("https://info.flipgrid.com")
    }

    /**
     * Ideally more tests...
     */
}