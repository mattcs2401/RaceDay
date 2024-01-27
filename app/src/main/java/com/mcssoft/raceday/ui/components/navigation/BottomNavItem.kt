package com.mcssoft.raceday.ui.components.navigation

import com.mcssoft.raceday.R

sealed class BottomNavItem(
    var route: String,
    var icon: Int,
    var title: String
) {
    data object Summary : BottomNavItem(
        Screens.SummaryScreen.route,
        R.drawable.ic_summary_24,
        "Summary"
    )
}
