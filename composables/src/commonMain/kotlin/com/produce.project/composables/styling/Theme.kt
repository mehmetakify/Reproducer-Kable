package com.produce.project.composables.styling

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = textColor,
    secondary = Teal200,
    background = lightBackground,
    onBackground = bottomNavBackground,
    surface = bottomNavSeparator,
    error = error,
    onSecondary = toggleOpen
)

private val DarkColorPalette = darkColors(
    primary = textColor,
    secondary = Teal200,
    background = darkBackground,
    onBackground = bottomNavBackgroundDark,
    surface = bottomNavSeparatorDark,
    error = error,
    onSecondary = toggleOpenDark
)

@Composable
fun Produce(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}