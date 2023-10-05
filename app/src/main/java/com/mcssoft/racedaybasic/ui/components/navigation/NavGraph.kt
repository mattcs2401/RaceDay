package com.mcssoft.racedaybasic.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcssoft.racedaybasic.ui.components.screens.MeetingsScreen
import com.mcssoft.racedaybasic.ui.components.screens.RacesScreen
import com.mcssoft.racedaybasic.ui.components.screens.SplashScreen
import com.mcssoft.racedaybasic.ui.meetings.MeetingsViewModel
import com.mcssoft.racedaybasic.ui.splash.SplashViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        // Splash screen.
        composable(route = Screen.SplashScreen.route
        ){
            val viewModel = hiltViewModel<SplashViewModel>()
            val state by viewModel.state.collectAsState()
            SplashScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        // Meetings screen.
        composable(route = Screen.MeetingsScreen.route) {
            val viewModel = hiltViewModel<MeetingsViewModel>()
            val state by viewModel.state.collectAsState()
            MeetingsScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        // Races screen.
        composable(
            route = Screen.RacesScreen.route + "meetingId={meetingId}",
            arguments = listOf(navArgument("meetingId") {
                type = NavType.LongType
            })
        ) {
            RacesScreen(navController = navController)
        }
//        // Runners screen.
//        composable(
//            route = Screen.RunnersScreen.route + "raceId={raceId}",
//            arguments = listOf(navArgument("raceId") {
//                type = NavType.LongType
//            })
//        ) {
//            RunnersScreen(navController = navController)
//        }

        // Summary screen (selected Runner items).
//        composable(route = Screen.SummaryScreen.route) {
//            SummaryScreen(navController = navController)
//        }

    }

}
