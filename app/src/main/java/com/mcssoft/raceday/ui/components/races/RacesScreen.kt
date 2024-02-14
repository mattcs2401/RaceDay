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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.dialog.TimeChangeDialog
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Failure
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Initialise
import com.mcssoft.raceday.ui.components.races.RacesState.Status.Success
import com.mcssoft.raceday.ui.components.races.components.MeetingHeader
import com.mcssoft.raceday.ui.components.races.components.RaceItem
import com.mcssoft.raceday.ui.theme.components.card.topappbar.lightRaceTopAppBarColours
import com.mcssoft.raceday.ui.theme.height64dp
import com.mcssoft.raceday.ui.theme.padding64dp
import com.mcssoft.raceday.utility.DateUtils

/**
 * @param state: Races state.
 * @param navController: The Navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RacesScreen(
    state: RacesState,
    navController: NavController,
    onEvent: (RacesEvent) -> Unit
) {
    val raceId = remember { mutableLongStateOf(0L) } // set by Race item onLongClick().

    val showTimeChangeDialog = remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        // Simply an initialiser value (only the picker can update the picker state).
        initialHour = 12, 0, true
    )

    val success = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(content = {
                        Text(
                            stringResource(id = R.string.label_races),
                            modifier = Modifier.weight(weight = 2f),
                        )
                    })
                },
                colors  = lightRaceTopAppBarColours,
                actions = {
                    IconButton(
                        onClick = {
                            backNavigate(
                                state = state,
                                navController = navController
                            )
                        })
                    {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding64dp
                )
                .background(MaterialTheme.colorScheme.surface)//background)
        ) {
            when (state.status) {
                is Initialise -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_init_title),
                        msgText = "Loading Races ...",
                        onDismiss = {}
                    )
                }
                is Failure -> {}
                is Success -> { success.value = true }
                else -> {}
            }
            if (showTimeChangeDialog.value) {
                TimeChangeDialog(
                    timeState = timePickerState,
                    showDialog = showTimeChangeDialog.value,
                    onDismissClicked = { showTimeChangeDialog.value = false },
                    onConfirmClicked = {
                        showTimeChangeDialog.value = false
                        onEvent(
                            RacesEvent.DateChange(
                                raceId.longValue,
                                DateUtils().formatHourMinutes(
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            )
                        )
                    }
                )
            }
            if (success.value) {
                OnSuccess(
                    state,
                    navController,
                    raceId,
                    showTimeChangeDialog
                )
            }
        }
    }
}

@Composable
private fun OnSuccess(
    state: RacesState,
    navController: NavController,
    raceId: MutableLongState,
    showTimeChange: MutableState<Boolean>
) {
    Header(state)
    Body(
        state,
        navController,
        raceId,
        showTimeChange
    )
}

@Composable
private fun Body(
    state: RacesState,
    navController: NavController,
    raceId: MutableLongState,
    showTimeChange: MutableState<Boolean>
) {
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
                    raceId.longValue = it.id
                    showTimeChange.value = true
                }
            )
        }
    }
}

@Composable
private fun Header(state: RacesState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height64dp)
    ) {
        state.meeting?.let { meeting ->
            MeetingHeader(
                meeting = meeting,
                backgroundColour = MaterialTheme.colorScheme.surface,
                borderColour = MaterialTheme.colorScheme.primary
            )
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
