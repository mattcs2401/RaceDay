package com.mcssoft.racedaybasic.ui.components.navigation

sealed class Screen(val route: String) {

    object SplashScreen : Screen("splash_screen")

    object MeetingsScreen : Screen("meetings_screen")

    object RacesScreen : Screen("races_screen")

    object RunnersScreen : Screen("runners_screen")

    object SettingsScreen : Screen("settings_screen")

    object SummaryScreen : Screen("summary_screen")

    object AdminScreen: Screen("admin_screen")
}
