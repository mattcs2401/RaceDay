package com.mcssoft.raceday.ui.components.navigation

import com.mcssoft.raceday.R

sealed class BottomNavItem(
    var route: String,
    var icon: Int,
    var title: String
) {
    data object Preferences : BottomNavItem(
        route = Screens.PreferencesScreen.route,
        icon = R.drawable.ic_settings_24,
        title = "Settings"
    )

    data object Summary : BottomNavItem(
        route = Screens.SummaryScreen.route,
        icon = R.drawable.ic_summary_24,
        title = "Summary"
    )
}
