package com.mcssoft.raceday.ui.components.runners

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.navigation.Screen
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.runners.components.RacesHeader
import com.mcssoft.raceday.ui.components.runners.components.RunnerItem
import com.mcssoft.raceday.ui.theme.height64dp
import com.mcssoft.raceday.ui.theme.padding64dp

@Composable
/**
 * @param state: Runners state.
 * @param navController: The Navigation.
 * @param onEvent: Call up to RunnersEvent in ViewModel.
 */
fun RunnersScreen(
    state: RunnersState,
    navController: NavController,
    onEvent: (RunnersEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_runners),
                backgroundColour = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        // As yet, haven't been able to make the meetingId param optional.
                        navController.navigate(Screen.RacesScreen.route + "meetingId=${0L}") {
                            popUpTo(route = Screen.RacesScreen.route) {
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
    ){
        // Race header.
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
                state.race?.let { race ->
                    RacesHeader(
                        race = race,
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
                    items = state.runners
                ) { runner ->
                    state.race?.let { race ->
                        RunnerItem(
                            race = race,
                            runner = runner,
                            onEvent = onEvent,
                            onItemClick = { }
                        )
                    }
                }
            }
//            RaceDaySnackbar(
//                snackbarHostState = scaffoldState.snackbarHostState,
//                modifier = Modifier.align(Alignment.BottomCenter),
//                onDismiss = {
//                    // TODO - some RunnerEvent ?
//                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
//                },
//            )
        }
    }
}