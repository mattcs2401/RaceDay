package com.mcssoft.raceday.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcssoft.raceday.ui.components.meetings.MeetingsScreen
import com.mcssoft.raceday.ui.components.meetings.MeetingsViewModel
import com.mcssoft.raceday.ui.components.races.RacesScreen
import com.mcssoft.raceday.ui.components.races.RacesViewModel
import com.mcssoft.raceday.ui.components.runner.RunnerScreen
import com.mcssoft.raceday.ui.components.runner.RunnerViewModel
import com.mcssoft.raceday.ui.components.runners.RunnersScreen
import com.mcssoft.raceday.ui.components.runners.RunnersViewModel
import com.mcssoft.raceday.ui.components.settings.SettingsScreen
import com.mcssoft.raceday.ui.components.settings.SettingsViewModel
import com.mcssoft.raceday.ui.components.splash.SplashScreen
import com.mcssoft.raceday.ui.components.splash.SplashViewModel
import com.mcssoft.raceday.ui.components.summary.SummaryScreen
import com.mcssoft.raceday.ui.components.summary.SummaryViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        // Splash screen.
        composable(route = Screens.SplashScreen.route
        ){
            val viewModel = hiltViewModel<SplashViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SplashScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        // Meetings screen.
        composable(route = Screens.MeetingsScreen.route) {
            val viewModel = hiltViewModel<MeetingsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            MeetingsScreen(
                state = state,
                navController = navController
            )
        }

        // Races screen.
        composable(
            // As yet, haven't been able to make the meetingId param optional.
            route = Screens.RacesScreen.route + "meetingId={meetingId}",
            arguments = listOf(navArgument("meetingId") {
                type = NavType.LongType
            })
        ) {
            val viewModel = hiltViewModel<RacesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            RacesScreen(
                state = state,
                navController = navController
            )
        }

        // Runners screen.
        composable(
            route = Screens.RunnersScreen.route + "raceId={raceId}",
            arguments = listOf(navArgument("raceId") {
                type = NavType.LongType
            })
        ) {
            val viewModel = hiltViewModel<RunnersViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            RunnersScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        // Runner screen.
        composable(
            route = Screens.RunnerScreen.route + "runnerId={runnerId}",
            arguments = listOf(navArgument("runnerId") {
                type = NavType.LongType
            })
        ) {
            val viewModel = hiltViewModel<RunnerViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            RunnerScreen(
                state = state,
//                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        // Settings screen.
        composable(
            route = Screens.SettingsScreen.route
        ){
            val viewModel = hiltViewModel<SettingsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SettingsScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        // Summary screen (selected Runner items).
        composable(
            route = Screens.SummaryScreen.route
        ){
            val viewModel = hiltViewModel<SummaryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SummaryScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

    }

}
/*
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(MY_ARG) { type = NavType.StringType }
            ),
            deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })
        ) {
            val arguments = it.arguments
            arguments?.getString(MY_ARG)?.let { message ->
                DetailsScreen(message = message)
            }
        }
 */
