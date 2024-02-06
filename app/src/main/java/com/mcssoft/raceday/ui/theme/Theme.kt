package com.mcssoft.raceday.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

// Reference: https://m2.material.io/design/color/applying-color-to-ui.html#top-and-bottom-app-bars

private val DarkColorPalette = darkColors(
    // TBA.
    primary = Purple200,
    primaryVariant = colourPrimaryVariant,
    secondary = Teal200
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = colourPrimary,
    primaryVariant = colourPrimaryVariant,

    // TBA.
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = Color.White, //RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,
)

//@SuppressLint("ConflictingOnColor")
//private val LightThemeColors = lightColors(
//    primary = Blue600,
//    primaryVariant = Blue400,
//    onPrimary = Black2,
//    secondary = Color.White,
//    secondaryVariant = Teal300,
//    onSecondary = Color.Black,
//    error = RedErrorDark,
//    onError = RedErrorLight,
//    background = Grey1,
//    onBackground = Color.Black,
//    surface = Color.White,
//    onSurface = Black2,
//)

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
            colors = colors,
            typography = Typography,
            shapes = RoundedCornerShapes,
            content = content
        )
    }


}