package com.mcssoft.raceday.ui.components.navigation

sealed class Screens(
    val route: String
) {
    data object SplashScreen : Screens("splash_screen")

    data object MeetingsScreen : Screens("meetings_screen")

    data object RacesScreen : Screens("races_screen")

    data object RunnersScreen : Screens("runners_screen")

    data object RunnerScreen : Screens("runner_screen")

    data object SummaryScreen : Screens("summary_screen")

    data object PreferencesScreen : Screens("preferences_screen")
}
