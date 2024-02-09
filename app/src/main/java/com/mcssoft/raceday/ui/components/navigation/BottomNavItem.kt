package com.mcssoft.raceday.ui.components.navigation

import com.mcssoft.raceday.R

sealed class BottomNavItem(
    var route: String,
    var icon: Int,
    var title: String
) {
    data object Settings: BottomNavItem(
        Screens.PreferencesScreen.route,
        R.drawable.ic_settings_24,
        "Preferences"
    )

    data object Summary: BottomNavItem(
        Screens.SummaryScreen.route,
        R.drawable.ic_summary_24,
        "Summary"
    )
}