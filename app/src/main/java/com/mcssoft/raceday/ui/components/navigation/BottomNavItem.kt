package com.mcssoft.raceday.ui.components.navigation

import com.mcssoft.raceday.R

sealed class BottomNavItem(
    var route: String,
    var icon: Int,
    var title: String
) {
    data object Settings: BottomNavItem(
        Screen.SettingsScreen.route,
        R.drawable.ic_settings_24,
        "Settings"
    )

    data object Summary: BottomNavItem(
        Screen.SummaryScreen.route,
        R.drawable.ic_summary_24,
        "Summary"
    )
}
