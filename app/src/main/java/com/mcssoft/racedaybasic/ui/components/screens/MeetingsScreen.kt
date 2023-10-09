package com.mcssoft.racedaybasic.ui.components.screens

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaybasic.ui.components.dialog.CommonDialog
import com.mcssoft.racedaybasic.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaybasic.ui.components.navigation.BottomBar
import com.mcssoft.racedaybasic.ui.components.navigation.Screen
import com.mcssoft.racedaybasic.ui.meetings.components.MeetingItem
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.ui.meetings.MeetingsEvent
import com.mcssoft.racedaybasic.ui.meetings.MeetingsState
import com.mcssoft.racedaybasic.ui.meetings.MeetingsViewModel
import com.mcssoft.racedaybasic.ui.meetings.components.MeetingsTopBar
import com.mcssoft.racedaybasic.ui.splash.SplashEvent
import com.mcssoft.racedaybasic.ui.splash.SplashState

@Composable
/**
 * @param state: Meetings state.
 * @param navController: The Navigation.
 * @param onEvent: Call up to MeetingsEvent in ViewModel.

 */
fun MeetingsScreen(
    state: MeetingsState,
    navController: NavController,
    onEvent: (MeetingsEvent) -> Unit   // TBA
) {
    val showRefreshDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    BackPressHandler(onBackPressed = {})

    Scaffold(
        topBar = {
            MeetingsTopBar(
                title = stringResource(id = R.string.label_meetings),
                title2 = state.mtgDate,
                backgroundColour = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        showRefreshDialog.value = true
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_refresh_24),
                            stringResource(id = R.string.lbl_icon_refresh)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(bottom = 64.dp)                        // TBA - allow for bottom bar.
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = state.body
                ) { meeting ->
                    MeetingItem(
                        meeting = meeting,
                        onItemClick = {
                            onEvent(MeetingsEvent.NavToRaces(meeting._id))
//                            if (!meeting.abandoned) {
                                navController.navigate(Screen.RacesScreen.route + "meetingId=${meeting._id}")
//                            }
                        }
                    )
                }
            }
            if (showRefreshDialog.value) {
                ShowRefreshDialog(show = showRefreshDialog, navController = navController)
            }
            when(state.status) {
                is MeetingsState.Status.Initialise -> {}
                is MeetingsState.Status.Loading -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_loading_title),
                        msgText = stringResource(id = R.string.dlg_loading_msg),
                        onDismiss = {}
                    )
                }
                is MeetingsState.Status.Failure -> {
                    showRefreshDialog.value = false
                    showErrorDialog.value = true
                    ShowErrorDialog(show = showErrorDialog, state.exception)
                }
                is MeetingsState.Status.Success -> {
                    // TBA.
                }
            }
        }
    }

}

@Composable
private fun ShowRefreshDialog(
    show: MutableState<Boolean>,
    navController: NavController,
) {
    CommonDialog(
        icon = R.drawable.ic_refresh_48,
        dialogTitle = stringResource(id = R.string.dlg_refresh_title),
        dialogText = stringResource(id = R.string.dlg_refresh_text),
        confirmButtonText = stringResource(id = R.string.lbl_btn_ok),
        dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
        onConfirmClicked = {
            show.value = !show.value
            navController.navigate(Screen.SplashScreen.route)
        },
        onDismissClicked = {
            show.value = !show.value
        }
    )
}

@Composable
private fun ShowErrorDialog(
    show: MutableState<Boolean>,
    exception: Exception?,  // message from any exception.
) {
    if (show.value) {
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = stringResource(id = R.string.dlg_error_title),
            dialogText = stringResource(id = R.string.dlg_error_msg_unknown),
            dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
            // TODO - exit the app ?
            onDismissClicked = {
                show.value = !show.value
            },
            confirmButtonText = stringResource(id = R.string.lbl_btn_refresh),
            onConfirmClicked = {
                show.value = !show.value
                /** TODO - TBA what we do here ? **/
            }
        )
    }
}

@Composable
//https://www.valueof.io/blog/intercept-back-press-button-in-jetpack-compose
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}
/*
  SnackBar example:
  -----------------
1. val snackBarHostState = remember { SnackbarHostState() }
2. if (appState.isRefreshing && appState.meetingsDownloaded) {
                LaunchedEffect(key1 = true) {
                    snackBarHostState.showSnackbar(
                        message = "Getting Runners from the Api.",
                        actionLabel = "",
                        duration = SnackbarDuration.Short
                    )
                }
                SnackBar(snackBarHostState = snackBarHostState)
     Get the Runner info from the Api.
    viewModel.setupRunnersFromApi(LocalContext.current)
}
 */
