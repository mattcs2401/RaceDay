package com.mcssoft.raceday.ui.components.summary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.navigation.Screen
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.summary.components.SummaryItem

@Composable
fun SummaryScreen(
    state: SummaryState,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_summary),
                backgroundColour = MaterialTheme.colors.primary,
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
        if (state.summaries.isEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text("Nothing to show yet.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            // Summaries listing.
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    items = state.summaries.sortedBy { summary ->
                        summary.raceStartTime
                    },
                    key = { smry ->
                        smry._id
                    }
                ) { summary ->
                    SummaryItem(
                        summary = summary,
                        onItemClick = { /* TBA */ }
                    )
                }
            }
        }
    }
}
