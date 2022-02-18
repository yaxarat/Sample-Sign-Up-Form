package dev.atajan.signupform.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorIndicatorText(
    errorText: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = errorText,
        style = MaterialTheme.typography.caption,
        fontWeight = FontWeight.Light,
        color = Color.Red,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorIndicatorTextPreview() {
    ErrorIndicatorText(errorText = "This is an error string.")
}