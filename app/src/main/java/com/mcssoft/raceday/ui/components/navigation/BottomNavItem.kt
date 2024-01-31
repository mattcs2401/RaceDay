package com.mcssoft.raceday.ui.components.navigation

import com.mcssoft.raceday.R

sealed class BottomNavItem(
    var route: String,
    var icon: Int,
    var title: String
) {
    data object Summary : BottomNavItem(
        route = Screens.SummaryScreen.route,
        icon = R.drawable.ic_summary_24,
        title = "Summary"
    )
}
