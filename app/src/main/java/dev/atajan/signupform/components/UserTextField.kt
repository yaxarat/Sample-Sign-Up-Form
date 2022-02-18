package dev.atajan.signupform.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import dev.atajan.signupform.ui.theme.TextFieldRed
import dev.atajan.signupform.ui.theme.paddingSmall

@Composable
fun UserTextField(
    label: String,
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maskTextInput: Boolean = false,
    softwareKeyboardController: SoftwareKeyboardController? = null,
    keyboardActionsOnDone: () -> Unit = { }
) {
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            onTextFieldValueChange(newValue)
        },
        label = {
            Text(text = label)
        },
        visualTransformation = if (maskTextInput) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = TextFieldRed,
            focusedLabelColor = TextFieldRed,
            cursorColor = TextFieldRed
        ),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                softwareKeyboardController?.hide()
                keyboardActionsOnDone()
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun UserTextFieldPreview() {
    Column(modifier = Modifier.padding(paddingSmall)) {
        UserTextField(
            label = "label",
            textFieldValue = "",
            onTextFieldValueChange = {}
        )

        UserTextField(
            label = "label",
            textFieldValue = "value present",
            onTextFieldValueChange = {},
            modifier = Modifier.padding(top = paddingSmall)
        )
    }
}