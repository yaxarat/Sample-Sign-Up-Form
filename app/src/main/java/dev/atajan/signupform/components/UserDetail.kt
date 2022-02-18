package dev.atajan.signupform.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import dev.atajan.signupform.common.USER_DETAIL_EMAIL
import dev.atajan.signupform.common.USER_DETAIL_NAME
import dev.atajan.signupform.common.USER_DETAIL_WEBSITE
import dev.atajan.signupform.ui.theme.paddingSmall

@Composable
fun UserDetail(
    website: String,
    name: String,
    email: String,
    onUrlClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (website.isNotEmpty()) {
            Text(
                text = website,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                color = Color.Blue,
                modifier = Modifier
                    .testTag(USER_DETAIL_WEBSITE)
                    .clickable { onUrlClick(website) }
                    .padding(bottom = paddingSmall)
            )
        }

        if (name.isNotEmpty()) {
            Text(
                text = name,
                modifier = Modifier
                    .testTag(USER_DETAIL_NAME)
                    .padding(bottom = paddingSmall)
            )
        }

        Text(
            text = email,
            modifier = Modifier.testTag(USER_DETAIL_EMAIL)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailPreview() {
    UserDetail(
        website = "website.link",
        name = "Aname",
        email = "email@domain.com",
        onUrlClick = {}
    )
}