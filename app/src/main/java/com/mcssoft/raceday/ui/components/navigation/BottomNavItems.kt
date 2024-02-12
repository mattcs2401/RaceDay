package com.mcssoft.raceday.ui.components.navigation

import com.mcssoft.raceday.R

sealed class BottomNavItems(
    var route: String,
    var icon: Int,
    var title: String
) {
    data object Settings: BottomNavItems(
        Screens.PreferencesScreen.route,
        R.drawable.ic_settings_24,
        "Preferences"
    )

    data object Summary: BottomNavItems(
        Screens.SummaryScreen.route,
        R.drawable.ic_summary_24,
        "Summary"
    )
}