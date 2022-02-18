package dev.atajan.signupform.screens.confirmation

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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.atajan.signupform.R
import dev.atajan.signupform.components.FullScreenCircularLoadingIndicator
import dev.atajan.signupform.components.HeaderText
import dev.atajan.signupform.components.ProgressButton
import dev.atajan.signupform.components.UserDetail
import dev.atajan.signupform.screens.confirmation.ConfirmationViewModel.Intention
import dev.atajan.signupform.screens.confirmation.ConfirmationViewModel.State
import dev.atajan.signupform.ui.theme.buttonHeight
import dev.atajan.signupform.ui.theme.paddingLarge
import dev.atajan.signupform.ui.theme.paddingSmall

@Composable
fun ConfirmationScreen(
    screenState: State,
    onIntention: (Intention) -> Unit,
) {

    val uriHandler = LocalUriHandler.current

    if (screenState.isLoading) {
        FullScreenCircularLoadingIndicator()
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = paddingLarge)
        ) {
            Column {
                HeaderText(
                    headerTitle = if (screenState.name.isEmpty()) {
                        stringResource(id = R.string.confirmation_header_title_no_name_provided)
                    } else {
                        String.format(
                            stringResource(id = R.string.confirmation_header_title),
                            screenState.name
                        )
                    },
                    headerBody = stringResource(id = R.string.confirmation_header_body),
                    modifier = Modifier
                        .padding(top = paddingLarge)
                        .fillMaxWidth()
                )

                UserDetail(
                    website = screenState.website,
                    name = screenState.name,
                    email = screenState.email,
                    onUrlClick = { uriHandler.openUri(it) },
                    modifier = Modifier
                        .padding(top = paddingSmall)
                        .fillMaxWidth()
                )
            }

            ProgressButton(
                buttonText = stringResource(id = R.string.button_sign_in),
                onCLick = {  },
                enabled = true,
                modifier = Modifier
                    .padding(bottom = paddingLarge)
                    .fillMaxWidth()
                    .height(buttonHeight)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmationScreenPreview() {
    ConfirmationScreen(
        screenState = State(
            name = "Yashar",
            email = "this@outlook.com",
            website = "atajan.dev",
            isLoading = false,
            showError = false
        ),
        onIntention = {}
    )
}