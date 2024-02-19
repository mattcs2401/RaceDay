package com.mcssoft.raceday.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mcssoft.raceday.ui.components.meetings.MeetingsScreen
import com.mcssoft.raceday.ui.components.meetings.MeetingsViewModel
import com.mcssoft.raceday.ui.components.preferences.PreferencesScreen
import com.mcssoft.raceday.ui.components.preferences.PreferencesViewModel
import com.mcssoft.raceday.ui.components.races.RacesScreen
import com.mcssoft.raceday.ui.components.races.RacesViewModel
import com.mcssoft.raceday.ui.components.runner.RunnerScreen
import com.mcssoft.raceday.ui.components.runner.RunnerViewModel
import com.mcssoft.raceday.ui.components.runners.RunnersScreen
import com.mcssoft.raceday.ui.components.runners.RunnersViewModel
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
        composable(route = Screens.SplashScreen.route) {
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
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        // Races screen.
        composable(route = Screens.RacesScreen.route) {
            val viewModel = hiltViewModel<RacesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            RacesScreen(
                state = state,
                navController = navController,
                onEvent = viewModel::onEvent
            )
        }

        // Runners screen.
        composable(route = Screens.RunnersScreen.route) {
            val viewModel = hiltViewModel<RunnersViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            RunnersScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        // Runner screen.
        composable(route = Screens.RunnerScreen.route) {
            val viewModel = hiltViewModel<RunnerViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            RunnerScreen(
                state = state,
                navController = navController
            )
        }

        // Summary screen (selected Runner items).
        composable(route = Screens.SummaryScreen.route) {
            val viewModel = hiltViewModel<SummaryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SummaryScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        // Preferences screen.
        composable(route = Screens.PreferencesScreen.route) {
            val viewModel = hiltViewModel<PreferencesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            PreferencesScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }
    }
}
