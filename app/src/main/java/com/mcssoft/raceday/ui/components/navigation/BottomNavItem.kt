package com.mcssoft.raceday.ui.components.navigation

import com.mcssoft.raceday.R

sealed class BottomNavItem(
    var route: String,
    var icon: Int,
    var title: String
) {
//    object Admin: BottomNavItem(
//        Screen.AdminScreen.route,
//        R.drawable.ic_admin_24,
//        "Admin"
//    )

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

    data object Trainer: BottomNavItem(
        Screen.TrainerScreen.route,
        R.drawable.ic_horse_variant_fast_24,
        "Trainer"
    )

    data object Jockey: BottomNavItem(
        Screen.JockeyScreen.route,
        R.drawable.ic_horse_human_24,
        "Jockey"
    )
}
