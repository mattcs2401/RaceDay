package com.mcssoft.raceday.ui.components.jockey

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.navigation.Screen
import com.mcssoft.raceday.ui.components.navigation.TopBar

@Composable
fun JockeyScreen(
    state: JockeyState,
    navController: NavController,
    onEvent: (JockeyEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_jockey),
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
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // Temporary chintzy under construction.
            Icon(
                painterResource(id = R.drawable.ic_under_const_48),
                stringResource(id = R.string.lbl_icon_home),
                Modifier.align(Alignment.Center)
            )
            Text("Under construction.",
                modifier = Modifier.align(Alignment.Center)
                    .padding(top = 64.dp)
            )
        }
        // Summaries listing.
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            items(
//                items = state.summaries
//            ) { summary ->
//                SummaryItem(
//                    summary = summary,
//                    onEvent = onEvent,
//                    onItemClick = { }
//                )
//            }
//        }
    }
}
