package com.angelodev.fitmatecompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = fitmateDarkTeal,
    secondary = PurpleGrey40,
    tertiary = Pink40

)

@Composable
fun FitmateComposeTheme(
    content: @Composable () -> Unit
) {
    // ALWAYS use light colors
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
