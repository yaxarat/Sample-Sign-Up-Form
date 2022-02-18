package dev.atajan.signupform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.atajan.signupform.navigation.Screen
import dev.atajan.signupform.screens.confirmation.ConfirmationScreen
import dev.atajan.signupform.screens.confirmation.ConfirmationViewModel
import dev.atajan.signupform.screens.registration.AccountRegisterScreen
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel
import dev.atajan.signupform.ui.theme.SignUpFormTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
}
