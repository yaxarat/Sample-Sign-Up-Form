package dev.atajan.signupform.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.tooling.preview.Preview
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
        Text(
            text = website,
            color = Color.Blue,
            modifier = Modifier
                .clickable { onUrlClick(website) }
                .padding(bottom = paddingSmall)
        )

        Text(
            text = name,
            modifier = Modifier.padding(bottom = paddingSmall)
        )

        Text(text = email)
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