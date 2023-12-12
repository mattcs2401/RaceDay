package com.mcssoft.raceday.ui.components.races

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.navigation.Screen
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Failure
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Loading
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Success
import com.mcssoft.raceday.ui.components.races.components.MeetingHeader
import com.mcssoft.raceday.ui.components.races.components.RaceItem
import com.mcssoft.raceday.ui.theme.height64dp
import com.mcssoft.raceday.ui.theme.padding64dp

@Composable
/**
 * @param state: Races state.
 * @param navController: The Navigation.
 */
fun RacesScreen(
    state: RacesState,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_races),
                backgroundColour = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
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
        // Meeting header.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {
            when (state.status) {
                is Loading -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_loading_title),
                        msgText = stringResource(id = R.string.dlg_loading_msg),
                        onDismiss = {}
                    )
                }
                is Failure -> {
                    // TBA.
                }
                is Success -> {
                    // Meeting details header.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height64dp)
                    ) {
                        state.meeting?.let { meeting ->
                            MeetingHeader(
                                meeting = meeting,
                                MaterialTheme.colors.background
                            )
                        }
                    }
                    // Races listing.
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = padding64dp)
                    ) {
                        items(
                            items = state.races
                        ) { race ->
                            RaceItem(
                                race = race,
                                onItemClick = {
                                    navController.navigate(
                                        Screen.RunnersScreen.route + "raceId=${race._id}"
                                    )
                                }
                            )
                        }
                    }
                }
                else -> {}
            }
        }
    }

}

