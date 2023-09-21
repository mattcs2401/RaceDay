package com.mcssoft.racedaybasic.ui.components.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.ui.components.dialog.ErrorDialog
import com.mcssoft.racedaybasic.ui.components.dialog.ExceptionErrorDialog
import com.mcssoft.racedaybasic.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaybasic.ui.components.dialog.ShowErrorDialog
import com.mcssoft.racedaybasic.ui.components.navigation.Screen
import com.mcssoft.racedaybasic.ui.splash.SplashState
import com.mcssoft.racedaybasic.ui.splash.SplashViewModel

/**
 * App starting point.
 */
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val showErrorDialog = remember { mutableStateOf(true) }

    Scaffold(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            if (!state.hasInternet) {
                ShowErrorDialog(
                    show = showErrorDialog,
                    title = "No Internet",
                    msg = "Check your device settings."
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
                        ShowErrorDialog(state = state)
                    }
                    is SplashState.Status.Failure -> {
                        ShowExceptionErrorDialog(state = state)
                    }
                    is SplashState.Status.Success -> {
                        if (state.baseFromApi && (!state.runnerFromApi)) {
    //                        viewModel.setupRunnersFromApi(LocalContext.current)
    //                        // Runners will continue to load in the background.
                            LaunchedEffect(key1 = true) {
                                navController.navigate(Screen.MeetingsScreen.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowErrorDialog(
//    show: MutableState<Boolean>,
    state: SplashState
) {
    val activity = LocalContext.current as Activity
//    if(show.value) {
        ErrorDialog(
//            show = show,
            dialogTitle = "An error occurred",
            message = "Error code: ${state.response}",
            dismissButtonText = "OK",
            onDismissClicked = {
                activity.finishAndRemoveTask()
//                show.value = !show.value
            }
        )
//    }
}

@Composable
fun ShowErrorDialog(
    show: MutableState<Boolean>,
    title: String,
    msg: String
) {
    val activity = LocalContext.current as Activity
//    if(show.value) {
        ErrorDialog(
//            show = show,
            dialogTitle = title,
            message = msg,
            dismissButtonText = "OK",
            onDismissClicked = {
                activity.finishAndRemoveTask()
//                show.value = !show.value
            }
        )
//    }
}

/**
 * This is basically for unrecoverable errors, as exits application.
 */
@Composable
fun ShowExceptionErrorDialog (
    state: SplashState
) {
    val activity = LocalContext.current as Activity
    if (state.exception != null) {
        ExceptionErrorDialog(
            dialogTitle = "An Exception occurred",
            exceptionMsg = state.exception.localizedMessage ?: "An unknown error occurred.",
            dismissButtonText = "OK",
            onDismissClicked = {
                activity.finishAndRemoveTask()
            }
        )
    } else {
        ExceptionErrorDialog(
            dialogTitle = state.customExType!!,
            exceptionMsg = state.customExMsg!!,
            dismissButtonText = "OK",
            onDismissClicked = {
                activity.finishAndRemoveTask()
            }
        )
    }
}