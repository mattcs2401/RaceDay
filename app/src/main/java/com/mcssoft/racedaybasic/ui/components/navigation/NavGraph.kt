package com.mcssoft.racedaybasic.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mcssoft.racedaybasic.ui.components.screens.MeetingsScreen
import com.mcssoft.racedaybasic.ui.components.screens.SplashScreen

@Composable
fun NavGraph(
//    context: Context    // TBA.
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        // Splash screen.
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        // Meetings screen.
        composable(route = Screen.MeetingsScreen.route) {
            MeetingsScreen(navController = navController)
        }

//        // Races screen.
//        composable(
//            route = Screen.RacesScreen.route + "meetingId={meetingId}",
//            arguments = listOf(navArgument("meetingId") {
//                type = NavType.LongType
//            })
//        ) {
//            RacesScreen(navController = navController)
//        }
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
