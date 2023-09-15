package com.mcssoft.racedaybasic.ui.components.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.ui.components.dialog.ErrorDialog
import com.mcssoft.racedaybasic.ui.components.dialog.ExceptionErrorDialog
import com.mcssoft.racedaybasic.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaybasic.ui.components.navigation.Screen
import com.mcssoft.racedaybasic.ui.splash.SplashState
import com.mcssoft.racedaybasic.ui.splash.SplashViewModel
import java.lang.Exception

/**
 * App starting point.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@RootNavGraph(start = true)
//@Destination
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val showErrorDialog = remember { mutableStateOf(true) }
    val showExErrorDialog = remember { mutableStateOf(true) }

    Scaffold(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
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
                    ShowErrorDialog(show = showErrorDialog, state.response)
                }
                is SplashState.Status.Failure -> {
                    ShowExceptionErrorDialog(show = showExErrorDialog, state.exception as Exception)
//                    val debug = "debug"
                    /** TODO - some sort of retry. **/
                }
                is SplashState.Status.Success -> {
//                    if (state.baseFromApi && (!state.runnerFromApi)) {
//                        viewModel.setupRunnersFromApi(LocalContext.current)
//                        // Runners will continue to load in the background.
                        LaunchedEffect(key1 = true) {
                            //navigator.navigate(MeetingsScreenDestination)
                            navController.navigate(Screen.MeetingsScreen.route)
                        }
                    }
//                    if (state.baseFromApi && state.runnerFromApi) {
//                        LaunchedEffect(key1 = true) {
//                            navigator.navigate(MeetingsScreenDestination)
//                        }
//                    }
//                }
            }
        }
    }
}

@Composable
fun ShowErrorDialog(
    show: MutableState<Boolean>,
    response: Int
) {
    if(show.value) {
        ErrorDialog(
            show = show,
            dialogTitle = "An error occurred",
            message = "Error code: $response",
            dismissButtonText = "OK",
            onDismissClicked = {
                show.value = !show.value
            }
        )
    }
}

@Composable
fun ShowExceptionErrorDialog (
    show: MutableState<Boolean>,
    exception: Exception
) {
    if(show.value) {
        ExceptionErrorDialog(
            dialogTitle = "An Exception occurred",
            exceptionMsg = exception.localizedMessage ?: "An unknown error occurred.",
            dismissButtonText = "OK",
            onDismissClicked = {
                show.value = !show.value
            }
        )
    }
}