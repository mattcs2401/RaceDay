package com.mcssoft.raceday.ui.theme.components.iconbutton

import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.mcssoft.raceday.utility.Constants.NO_COLOUR

val lightIconButtonColours = IconButtonColors(
    containerColor = lightColorScheme().secondary,
    contentColor = Color.White,
    disabledContainerColor = NO_COLOUR,
    disabledContentColor = NO_COLOUR
)

val lightMeetingIconButtonColours = IconButtonColors(
    containerColor = lightColorScheme().primaryContainer,
    contentColor = lightColorScheme().onPrimaryContainer,
    disabledContainerColor = lightColorScheme().primaryContainer,
    disabledContentColor = lightColorScheme().onPrimary
)

