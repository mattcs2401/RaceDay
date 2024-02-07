package com.mcssoft.raceday.ui.components.races

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.dialog.TimeChangeDialog
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Failure
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Loading
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Success
import com.mcssoft.raceday.ui.components.races.components.MeetingHeader
import com.mcssoft.raceday.ui.components.races.components.RaceItem
import com.mcssoft.raceday.ui.theme.height64dp
import com.mcssoft.raceday.ui.theme.padding64dp
import com.mcssoft.raceday.utility.DateUtils

/**
 * @param state: Races state.
 * @param navController: The Navigation.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RacesScreen(
    state: RacesState,
    navController: NavController,
    onEvent: (RacesEvent) -> Unit
) {
    var raceId: Long = 0 // set by Race item onLongClick().

    val showTimeChangeDialog = remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        // simply an initialise value (only the picker can update picker state).
        initialHour = 12, 0, true
    )

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_races),
                backgroundColour = MaterialTheme.colors.primary,
                titleColour = Color.White,
                actions = {
                    IconButton(onClick = {
                        backNavigate(navController = navController, state = state)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_home_24),
                            stringResource(id = R.string.lbl_icon_home),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {
            if (showTimeChangeDialog.value) {
                TimeChangeDialog(
                    timeState = timePickerState,
                    showDialog = showTimeChangeDialog.value,
                    onDismissClicked = { showTimeChangeDialog.value = false },
                    onConfirmClicked = {
                        showTimeChangeDialog.value = false
                        onEvent(
                            RacesEvent.DateChange(
                                raceId,
                                DateUtils().formatHourMinutes(
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            )
                        )
                    }
                )
            }
            when (state.status) {
                is Loading -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_loading_races),
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
                            items = state.races,
                            key = { it.id }
                        ) { race ->
                            RaceItem(
                                race = race,
                                onItemClick = {
                                    navController.navigate(
                                        Screens.RunnersScreen.route + "raceId=${race.id}"
                                    )
                                },
                                onItemLongClick = {
                                    raceId = it.id
                                    showTimeChangeDialog.value = true
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

fun backNavigate(
    navController: NavController,
    state: RacesState
) {
    navController.navigate(
        Screens.MeetingsScreen.route + "fromApi=${state.fromApi}"
    ) {
        popUpTo(route = Screens.MeetingsScreen.route) {
            inclusive = true
        }
    }
}
