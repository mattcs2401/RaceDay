package com.mcssoft.raceday.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

// Reference: https://m2.material.io/design/color/applying-color-to-ui.html#top-and-bottom-app-bars

private val DarkColorPalette = darkColorScheme(
//    primary = Purple200,
//    primaryVariant = colourPrimaryVariant,
//    secondary = Teal200
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColorScheme(
    primary = colourPrimary,
    onPrimary = colourOnPrimary,
    secondary = colourSecondary,
    onSecondary = colourOnSecondary,
    error = colourError,
    onError = colourOnError,
    background = colourBackground,
    onBackground = colourOnBackground,
    surface = colourSurface,
    onSurface = colourOnSurface,
)

@OptIn(ExperimentalMaterial3Api::class)
val lightTopAppBarColours = TopAppBarColors(
    containerColor = lightColorScheme().primary,
    titleContentColor = lightColorScheme().onPrimary,
    scrolledContainerColor = lightColorScheme().surface, // TBA
    navigationIconContentColor = lightColorScheme().surface, // TBA
    actionIconContentColor = lightColorScheme().surface // TBA
)

val lightCardColours = CardColors(
    containerColor = lightColorScheme().primary,
    contentColor = lightColorScheme().onPrimary,
    disabledContainerColor = lightColorScheme().surface, // TBA
    disabledContentColor = lightColorScheme().onSurface // TBA
)

val lightIconButtonColours = IconButtonColors(
    containerColor = lightColorScheme().secondary,
    contentColor = Color.White,
    disabledContainerColor = lightColorScheme().surface,
    disabledContentColor = lightColorScheme().onSurface
)

@Composable
fun RaceDayBasicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}