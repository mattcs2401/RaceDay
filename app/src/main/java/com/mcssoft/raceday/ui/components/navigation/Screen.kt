package com.mcssoft.raceday.ui.components.navigation

sealed class Screen(val route: String) {

    data object SplashScreen : Screen("splash_screen")

    data object MeetingsScreen : Screen("meetings_screen")

    data object RacesScreen : Screen("races_screen")

    data object RunnersScreen : Screen("runners_screen")

    data object SummaryScreen : Screen("summary_screen")

    data object TrainerScreen : Screen("trainer_screen")

    data object JockeyScreen : Screen("jockey_screen")

    data object SettingsScreen : Screen("settings_screen")
}
