package com.mcssoft.raceday.ui.theme.components.card

import androidx.compose.material3.CardColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.mcssoft.raceday.utility.Constants.NO_COLOUR

/*
  Note: These colours are all trial and error ATT. Not sure I like the default Material3 colours.
 */

val lightCardColours = CardColors(
    containerColor = lightColorScheme().primary,
    contentColor = lightColorScheme().onPrimary,
    disabledContainerColor = NO_COLOUR, // TBA
    disabledContentColor = NO_COLOUR // TBA
)

val lightMeetingCardColours = CardColors(
    containerColor = lightColorScheme().secondaryContainer,
    contentColor = lightColorScheme().onSecondaryContainer,
    disabledContainerColor = NO_COLOUR, // TBA
    disabledContentColor = NO_COLOUR // TBA
)

val lightRaceCardColours = CardColors(
    containerColor = lightColorScheme().tertiaryContainer,
    contentColor = lightColorScheme().onTertiaryContainer,
    disabledContainerColor = NO_COLOUR, // TBA
    disabledContentColor = NO_COLOUR // TBA
)

val lightRunnersCardColours = CardColors(
    containerColor = lightColorScheme().primary,
    contentColor = lightColorScheme().onPrimary,
    disabledContainerColor = NO_COLOUR, // TBA
    disabledContentColor = NO_COLOUR // TBA
)

val lightPreferencesCardColours = CardColors(
    containerColor = lightColorScheme().primary,
    contentColor = lightColorScheme().onPrimary,
    disabledContainerColor = NO_COLOUR, // TBA
    disabledContentColor = NO_COLOUR // TBA
)

val lightSummaryCurrentCardColours = CardColors(
    containerColor = lightColorScheme().primary,
    contentColor = lightColorScheme().onPrimary,
    disabledContainerColor = NO_COLOUR, // TBA
    disabledContentColor = NO_COLOUR // TBA
)

val lightSummaryPreviousCardColours = CardColors(
    containerColor = darkColorScheme().tertiary,
    contentColor = darkColorScheme().onTertiary,
    disabledContainerColor = NO_COLOUR, // TBA
    disabledContentColor = NO_COLOUR // TBA
)

