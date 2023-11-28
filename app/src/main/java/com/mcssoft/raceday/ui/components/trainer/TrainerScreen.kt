package com.mcssoft.raceday.ui.components.trainer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.navigation.Screen
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.trainer.components.TrainerItem

@Composable
fun TrainerScreen(
    state: TrainerState,
    navController: NavController,
    onEvent: (TrainerEvent) -> Unit
) {
//    val scaffoldState = rememberScaffoldState()

    Scaffold(
//        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_trainer),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    // As yet, haven't been able to make the meetingId param optional.
                    navController.navigate(Screen.MeetingsScreen.route) {
                        popUpTo(route = Screen.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // As yet, haven't been able to make the meetingId param optional.
                        navController.navigate(Screen.MeetingsScreen.route) {
                            popUpTo(route = Screen.MeetingsScreen.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_home_24),
                            stringResource(id = R.string.lbl_icon_home)
                        )
                    }
                }
            )
        }
    ) {
        // Summaries listing.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items = state.trainers
            ) { trainer ->
                TrainerItem(
                    trainer = trainer,
                    onEvent = onEvent,
                    onItemClick = {}
                )
            }
        }
    }
}

