package dev.atajan.signupform.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.atajan.signupform.common.HEADER_TEXT
import dev.atajan.signupform.ui.theme.SignUpFormTheme
import dev.atajan.signupform.ui.theme.paddingSmall

@Composable
fun HeaderText(
    headerTitle: String,
    headerBody: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.testTag(HEADER_TEXT)
    ) {
        Text(
            text = headerTitle,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = headerBody,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                top = paddingSmall
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderTextPreview() {
    HeaderText(
        headerTitle = "This is a title",
        headerBody = " And this right here will be the body."
    )
}