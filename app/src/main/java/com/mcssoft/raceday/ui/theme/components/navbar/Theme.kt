package com.mcssoft.raceday.ui.theme.components.navbar

import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.lightColorScheme
import com.mcssoft.raceday.utility.Constants

val lightBottomNavBarColours = NavigationBarItemColors(
    selectedIconColor = lightColorScheme().primary,
    selectedTextColor = lightColorScheme().onPrimary,
    selectedIndicatorColor = Constants.NO_COLOUR,
    unselectedIconColor = lightColorScheme().tertiary,
    unselectedTextColor = lightColorScheme().onTertiary,
    disabledIconColor = Constants.NO_COLOUR,
    disabledTextColor = Constants.NO_COLOUR
)