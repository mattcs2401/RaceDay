package com.mcssoft.raceday.ui.components.meetings

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.CommonDialog
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.meetings.MeetingsState.Status
import com.mcssoft.raceday.ui.components.meetings.components.MeetingItem
import com.mcssoft.raceday.ui.components.meetings.components.MeetingsTopBar
import com.mcssoft.raceday.ui.components.navigation.BottomBar
import com.mcssoft.raceday.ui.components.navigation.Screens

/**
 * @param state: Meetings state.
 * @param navController: The Navigation.
 */
@Composable
fun MeetingsScreen(
    state: MeetingsState,
    navController: NavController
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
                .padding(bottom = 64.dp) // TBA - allow for bottom bar.
        ) {
            if (showRefreshDialog.value) {
                ShowRefreshDialog(show = showRefreshDialog, navController = navController)
            }
            when (state.status) {
                is Status.Initialise -> {}
                is Status.Loading -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_loading_meetings),
                        msgText = stringResource(id = R.string.dlg_loading_msg),
                        onDismiss = {}
                    )
                }
                is Status.Failure -> {
                    showRefreshDialog.value = false
                    showErrorDialog.value = true
                    ShowErrorDialog(
                        show = showErrorDialog,
                        state.exception
                    )
                }
                is Status.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = state.body,
                            key = { it.id }
                        ) { meeting ->
                            MeetingItem(
                                meeting = meeting,
                                onItemClick = {
                                    navController.navigate(
                                        Screens.RacesScreen.route + "meetingId=${meeting.id}"
                                    )
                                }
                            )
                        }
                    }
                }
                else -> {} // ??
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
            navController.navigate(Screens.SplashScreen.route)
        },
        onDismissClicked = {
            show.value = !show.value
        }
    )
}

@Composable
private fun ShowErrorDialog(
    show: MutableState<Boolean>,
    exception: Exception?, // message from any exception.
) {
    if (show.value) {
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = stringResource(id = R.string.dlg_error_title),
            dialogText = exception?.message ?: stringResource(id = R.string.dlg_error_msg_unknown),
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

// https://www.valueof.io/blog/intercept-back-press-button-in-jetpack-compose
@Composable
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
