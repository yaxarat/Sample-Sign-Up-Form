package dev.atajan.signupform.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.atajan.signupform.R
import dev.atajan.signupform.common.UserInput
import dev.atajan.signupform.components.HeaderText
import dev.atajan.signupform.components.ProgressButton
import dev.atajan.signupform.components.UserTextField
import dev.atajan.signupform.components.ValidateableUserTextField
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.Intention
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.RegistrationInputError.InvalidPassword
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.RegistrationInputError.InvalidWebUrl
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.State
import dev.atajan.signupform.ui.theme.buttonHeight
import dev.atajan.signupform.ui.theme.paddingLarge
import dev.atajan.signupform.ui.theme.paddingMedium
import dev.atajan.signupform.ui.theme.paddingSmall
import dev.atajan.signupform.ui.theme.textFieldHeight

@Composable
fun AccountRegisterScreen(
    screenState: State,
    onIntention: (Intention) -> Unit,
    navigateToConfirmationScreen: (String) -> Unit
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = paddingLarge)
    ) {

        Column {
            HeaderText(
                headerTitle = stringResource(id = R.string.account_registration_header_title),
                headerBody = stringResource(id = R.string.account_registration_header_body),
                modifier = Modifier
                    .padding(top = paddingLarge)
                    .fillMaxWidth()
            )

            UserTextField(
                label = stringResource(id = R.string.user_field_first_name),
                textFieldValue = screenState.name,
                onTextFieldValueChange = { name ->
                    onIntention(Intention.UpdateName(name))
                },

                modifier = Modifier
                    .padding(
                        top = paddingMedium,
                        bottom = paddingSmall
                    )
                    .fillMaxWidth()
                    .height(textFieldHeight),
                softwareKeyboardController = softwareKeyboardController
            )

            ValidateableUserTextField(
                label = stringResource(id = R.string.user_field_email),
                fieldValue = screenState.email,
                onTextFieldValueChange = { email ->
                    onIntention(Intention.UpdateEmail(email))
                },

                modifier = Modifier
                    .padding(bottom = paddingSmall)
                    .fillMaxWidth()
                    .height(textFieldHeight),
                softwareKeyboardController = softwareKeyboardController
            )

            ValidateableUserTextField(
                label = stringResource(id = R.string.user_field_password),
                fieldValue = screenState.password,
                onTextFieldValueChange = { password ->
                    onIntention(Intention.UpdatePassword(password))
                },
                modifier = Modifier
                    .padding(bottom = paddingSmall)
                    .fillMaxWidth()
                    .height(textFieldHeight),
                maskTextInput = true,
                softwareKeyboardController = softwareKeyboardController
            )

            ValidateableUserTextField(
                label = stringResource(id = R.string.user_field_website),
                fieldValue = screenState.website,
                onTextFieldValueChange = { website ->
                    onIntention(Intention.UpdateWebsite(website))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(textFieldHeight),
                softwareKeyboardController = softwareKeyboardController
            )
        }

        ProgressButton(
            buttonText = stringResource(id = R.string.button_submit),
            onCLick = {
                onIntention(
                    Intention.SaveUserInformation {
                        navigateToConfirmationScreen(screenState.email.value)
                    }
                )
            },
            enabled = screenState.let { state ->
                state.email.value.trim().isNotEmpty() &&
                        state.password.value.trim().isNotEmpty()
            },
            modifier = Modifier
                .padding(bottom = paddingLarge)
                .fillMaxWidth()
                .height(buttonHeight)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountRegisterScreenPreview() {
    AccountRegisterScreen(
        screenState = State(
            name = "Yashar",
            email = UserInput(value = "this@outlook.com", error = null),
            password = UserInput(value = "1234", error = InvalidPassword),
            website = UserInput(value = "i love dogs", error = InvalidWebUrl)
        ),
        onIntention = {},
        navigateToConfirmationScreen = {}
    )
}