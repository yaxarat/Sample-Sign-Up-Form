package dev.atajan.signupform.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import dev.atajan.signupform.common.UserInput
import dev.atajan.signupform.screens.registration.AccountRegistrationViewModel.RegistrationInputError
import dev.atajan.signupform.ui.theme.paddingSmall

@Composable
fun ValidateableUserTextField(
    label: String,
    fieldValue: UserInput<String, RegistrationInputError>,
    onTextFieldValueChange: (UserInput<String, RegistrationInputError>) -> Unit,
    modifier: Modifier = Modifier,
    maskTextInput: Boolean = false,
    softwareKeyboardController: SoftwareKeyboardController? = null,
    keyboardActionsOnDone: () -> Unit = { }
) {
    Column(horizontalAlignment = Alignment.Start) {
        OutlinedTextField(
            value = fieldValue.value,
            onValueChange = { newValue ->
                onTextFieldValueChange(
                    UserInput(
                        value = newValue,
                        error = null
                    )
                )
            },
            label = {
                Text(text = label)
            },
            visualTransformation = if (maskTextInput) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            modifier = modifier,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    softwareKeyboardController?.hide()
                    keyboardActionsOnDone()
                }
            )
        )

        fieldValue.error?.let { error ->
            ErrorIndicatorText(
                errorText = stringResource(id = error.message),
                modifier = Modifier.padding(bottom = paddingSmall)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ValidateableUserTextFieldPreview() {
    Column(modifier = Modifier.padding(paddingSmall)) {
        ValidateableUserTextField(
            label = "label",
            fieldValue = UserInput(
                value = "value",
                error = null
            ),
            onTextFieldValueChange = {},
            maskTextInput = false,
            softwareKeyboardController = null,
            keyboardActionsOnDone = {}
        )

        ValidateableUserTextField(
            label = "label",
            fieldValue = UserInput(
                value = "yashar@out",
                error = RegistrationInputError.InvalidEmail
            ),
            onTextFieldValueChange = {},
            maskTextInput = false,
            softwareKeyboardController = null,
            keyboardActionsOnDone = {},
            modifier = Modifier.padding(top = paddingSmall)
        )
    }
}