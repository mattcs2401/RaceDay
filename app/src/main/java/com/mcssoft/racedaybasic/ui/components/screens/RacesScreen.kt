package com.mcssoft.racedaybasic.ui.components.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.ui.components.dialog.CommonDialog
import com.mcssoft.racedaybasic.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaybasic.ui.components.navigation.Screen
import com.mcssoft.racedaybasic.ui.components.navigation.TopBar
import com.mcssoft.racedaybasic.ui.components.races.RacesEvent
import com.mcssoft.racedaybasic.ui.components.races.RacesState
import com.mcssoft.racedaybasic.ui.meetings.components.MeetingHeader
import com.mcssoft.racedaybasic.ui.components.races.RacesState.Status.*
import com.mcssoft.racedaybasic.ui.components.races.RacesViewModel
import com.mcssoft.racedaybasic.ui.components.races.components.RaceItem
import com.mcssoft.racedaybasic.ui.theme.height64dp
import com.mcssoft.racedaybasic.ui.theme.padding64dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
/**
 * @param navController: The Navigation.
 * @param viewModel: The associated view model.
 */
fun RacesScreen(
    navController: NavController,
    viewModel: RacesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_races),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navController.navigate(Screen.MeetingsScreen.route) {
                        popUpTo(route = Screen.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height64dp)
            ) {
                state.mtg?.let { meeting ->
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
                            navController.navigate(Screen.RunnersScreen.route + "raceId=${race._id}")
                        }
                    )
                }
            }
            ManageState(
                racesState = state,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun ManageState(
    racesState: RacesState,
    viewModel: RacesViewModel
) {
    val errorDialogShow = remember { mutableStateOf(false) }

    when (racesState.status) {
        is Initialise -> {}
        is Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_loading_title),
                msgText = stringResource(id = R.string.dlg_loading_msg),
                onDismiss = {}
            )
        }
        is Failure -> {
            errorDialogShow.value = true
            ShowErrorDialog(
                errorDialogShow,
                racesState.mtgId,
                viewModel = viewModel
            )
        }
        is Success -> {/* TBA */}
    }
}

@Composable
private fun ShowErrorDialog(
    showError: MutableState<Boolean>,
    mtgId: Long,
    viewModel: RacesViewModel
) {
    if (showError.value) {
        showError.value = !showError.value
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = stringResource(id = R.string.dlg_error_title),
            dialogText = "Unable to get the Races listing.",
            dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
            onDismissClicked = {
                viewModel.onEvent(RacesEvent.Cancel)
            },
            confirmButtonText = stringResource(id = R.string.lbl_btn_retry),
            onConfirmClicked = {
                viewModel.onEvent(RacesEvent.Retry(mtgId))
            }
        )
    }
}
