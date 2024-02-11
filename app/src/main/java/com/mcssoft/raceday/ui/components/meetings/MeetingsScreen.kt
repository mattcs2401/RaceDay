package com.mcssoft.raceday.ui.components.meetings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.CommonDialog
import com.mcssoft.raceday.ui.components.meetings.MeetingsState.Status
import com.mcssoft.raceday.ui.components.meetings.components.MeetingItem
import com.mcssoft.raceday.ui.components.navigation.BottomBar
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.theme.padding64dp
import com.mcssoft.raceday.ui.theme.padding8dp
import com.mcssoft.raceday.ui.theme.topappbar.lightMeetingTopAppBarColours

/**
 * @param state: Meetings state.
 * @param navController: The Navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MeetingsScreen(
    state: MeetingsState,
    navController: NavController
) {
    val showRefreshDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    val showMeetingRefreshDialog = remember { mutableStateOf(false) }

    BackPressHandler(onBackPressed = {})

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(content = {
                        Text(
                            stringResource(id = R.string.label_meetings),
                            modifier = Modifier.weight(weight = 2f),
                        )
                        Text(
                            state.mtgDate,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(weight = 3f),
                        )
                    })
                },
                actions = {
                    IconButton(
                        enabled = state.canRefresh,
                        onClick = {
                            showRefreshDialog.value = true
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_refresh_24),
                            stringResource(id = R.string.lbl_icon_refresh)
                        )
                    }
                },
                colors = lightMeetingTopAppBarColours
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                displayLabels = false
            )
        }
    ) {
        if (showRefreshDialog.value) {
            ShowRefreshDialog(
                show = showRefreshDialog,
                navController = navController
            )
        }
        if (showMeetingRefreshDialog.value) {
            ShowMeetingRefreshDialog(
                show = showMeetingRefreshDialog
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = padding8dp,
                    bottom = padding64dp)
        ) {
            when (state.status) {
                is Status.Failure -> {
                    showMeetingRefreshDialog.value = false
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
                                },
                                onItemLongClick = {
//                                    venueMnemonic = it.venueMnemonic ?: ""
                                    showMeetingRefreshDialog.value = true
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
fun ShowMeetingRefreshDialog(
    show: MutableState<Boolean>
) {
    val context = LocalContext.current
    CommonDialog(
        icon = R.drawable.ic_refresh_48,
        dialogTitle = stringResource(id = R.string.dlg_refresh_meeting_title),
        dialogText = stringResource(id = R.string.dlg_refresh_meeting_text),
        confirmButtonText = stringResource(id = R.string.lbl_btn_ok),
        dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
        onConfirmClicked = {
            show.value = !show.value
            Toast.makeText(context, "Functionality not implemented yet.", Toast.LENGTH_SHORT).show()
//            onEvent(MeetingEvent.RefreshMeeting(venueMnemonic))
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
