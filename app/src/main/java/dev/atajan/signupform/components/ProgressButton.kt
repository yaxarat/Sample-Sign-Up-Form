package dev.atajan.signupform.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import dev.atajan.signupform.common.PROGRESS_BUTTON
import dev.atajan.signupform.ui.theme.ButtonGradientLeft
import dev.atajan.signupform.ui.theme.ButtonGradientRight

@Composable
fun ProgressButton(
    buttonText: String,
    onCLick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onCLick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(),
        modifier = modifier.testTag(PROGRESS_BUTTON)
    ) {
        Box(
            modifier = Modifier
                .background(Brush.horizontalGradient(colors = listOf(ButtonGradientLeft, ButtonGradientRight)))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.background
            )
        }
    }
}