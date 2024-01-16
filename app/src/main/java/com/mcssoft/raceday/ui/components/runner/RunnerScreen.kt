package com.mcssoft.raceday.ui.components.runner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.runner.RunnerState.Status
import com.mcssoft.raceday.ui.theme.padding64dp

@Composable
/**
 * @param state: Runners state.
 * @param navController: The Navigation.
 * @param onEvent: Call up to RunnersEvent in ViewModel.
 */
fun RunnerScreen(
    state: RunnerState,
    navController: NavController,
//    onEvent: (RunnerEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_runner),
                backgroundColour = MaterialTheme.colors.primary,
                actions = {
//                    IconButton(onClick = {
//                        // As yet, haven't been able to make the meetingId param optional.
//                        navController.navigate(Screens.RunnersScreen.route) {
//                            popUpTo(route = Screens.RunnersScreen.route) {
//                                inclusive = true
//                            }
//                        }
//                    }) {
//                        Icon(
//                            painterResource(id = R.drawable.ic_home_24),
//                            stringResource(id = R.string.lbl_icon_home)
//                        )
//                    }
                }
            )
        }
    ){
        // Race header.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {
            when(state.status) {
                is Status.Loading -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_loading_runner),
                        msgText = stringResource(id = R.string.dlg_loading_msg),
                        onDismiss = {}
                    )
                }
                is Status.Failure -> { /* TBA */ }
                is Status.Success -> {
                    // Runner detail.
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = padding64dp)
                    ) {
                        ConstraintLayout(constraintSet) {

                        }
                    }
                }
                else -> {}
            }

        }
    }
}

private val constraintSet = ConstraintSet {

}
