package com.mcssoft.raceday.ui.components.splash

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.ErrorDialog
import com.mcssoft.raceday.ui.components.dialog.ExceptionErrorDialog
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.navigation.Screens

/**
 * App UI starting point.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    state: SplashState,
    navController: NavController,
    onEvent: (SplashEvent) -> Unit
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (!state.hasInternet) {
                ShowErrorDialog(
                    state = state,
                    onEvent = onEvent
                )
            } else {
                when (state.status) {
                    is SplashState.Status.Initialise -> {}
                    is SplashState.Status.Loading -> {
                        LoadingDialog(
                            titleText = stringResource(id = R.string.dlg_init_title),
                            msgText = state.loadingMsg,
                            onDismiss = {}
                        )
                    }
                    is SplashState.Status.Error -> {
                        ShowErrorDialog(
                            state = state,
                            onEvent = onEvent
                        )
                    }
                    is SplashState.Status.Failure -> {
                        ShowExceptionErrorDialog(
                            state = state,
                            onEvent = onEvent
                        )
                    }
                    is SplashState.Status.Success -> {
                        LaunchedEffect(key1 = true) {
                            // Coming from the SplashScreen we have downloaded from the Api.
                            navController.navigate(Screens.MeetingsScreen.route)
                        }
                        onEvent(SplashEvent.SetRunners)
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun ShowErrorDialog(
    state: SplashState,
    onEvent: (SplashEvent) -> Unit
) {
    val activity = LocalContext.current as Activity
    ErrorDialog(
        dialogTitle = "An error occurred",
        message = "Error code: ${state.response}",
        dismissButtonText = "OK",
        onDismissClicked = {
            onEvent(SplashEvent.Error(activity))
        }
    )
}

@Composable
fun ShowErrorDialog(
    title: String,
    msg: String,
    onEvent: (SplashEvent) -> Unit
) {
    val activity = LocalContext.current as Activity
    ErrorDialog(
        dialogTitle = title,
        message = msg,
        dismissButtonText = "OK",
        onDismissClicked = {
            onEvent(SplashEvent.Error(activity))
        }
    )
}

/**
 * This is basically for unrecoverable errors, as exits application.
 */
@Composable
fun ShowExceptionErrorDialog(
    state: SplashState,
    onEvent: (SplashEvent) -> Unit
) {
    val activity = LocalContext.current as Activity

    if (state.exception != null) {
        ExceptionErrorDialog(
            dialogTitle = "An Exception occurred",
            exceptionMsg = state.exception.localizedMessage ?: "An unknown error occurred.",
            dismissButtonText = "OK",
            onDismissClicked = {
                onEvent(SplashEvent.Error(activity))
            }
        )
    } else {
        ExceptionErrorDialog(
            dialogTitle = state.customExType!!,
            exceptionMsg = state.customExMsg!!,
            dismissButtonText = "OK",
            onDismissClicked = {
                onEvent(SplashEvent.Error(activity))
            }
        )
    }
}
