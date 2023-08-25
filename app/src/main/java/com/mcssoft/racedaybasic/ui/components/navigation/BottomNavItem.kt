package com.mcssoft.racedaybasic.ui.components.navigation

import com.mcssoft.racedaybasic.R

/** TBA **/

sealed class BottomNavItem(
    var route: String,
    var icon: Int,
    var title: String
) {
    object Admin: BottomNavItem(
        Screen.AdminScreen.route,
        R.drawable.ic_admin_24,
        "Admin"
    )

    object Settings: BottomNavItem(
        Screen.SettingsScreen.route,
        R.drawable.ic_settings_24,
        "Settings"
    )

    object Summary: BottomNavItem(
        Screen.SummaryScreen.route,
        R.drawable.ic_summary_24,
        "Summary"
    )

}
