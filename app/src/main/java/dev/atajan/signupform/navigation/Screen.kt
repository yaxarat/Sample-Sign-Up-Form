package dev.atajan.signupform.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object AccountRegistration: Screen(
        route = "accountRegistration",
        arguments = listOf()
    )

    object Confirmation: Screen(
        route = "confirmation",
        arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
            }
        )
    )
}
