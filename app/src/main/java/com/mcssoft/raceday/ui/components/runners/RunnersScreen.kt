package com.mcssoft.raceday.ui.components.runners

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.runners.RunnersState.Status
import com.mcssoft.raceday.ui.components.runners.components.RacesHeader
import com.mcssoft.raceday.ui.components.runners.components.RunnerItem
import com.mcssoft.raceday.ui.theme.components.card.topappbar.lightRunnersTopAppBarColours
import com.mcssoft.raceday.ui.theme.height64dp
import com.mcssoft.raceday.ui.theme.padding64dp

/**
 * @param state: Runners state.
 * @param navController: The Navigation.
 * @param onEvent: Call up to RunnersEvent in ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RunnersScreen(
    state: RunnersState,
    navController: NavController,
    onEvent: (RunnersEvent) -> Unit
) {
    val success = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(content = {
                        Text(
                            stringResource(id = R.string.label_runners),
                            modifier = Modifier.weight(weight = 2f),
                        )
                    })
                },
                colors = lightRunnersTopAppBarColours,
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
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            when (state.status) {
                is Status.Initialise -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_init_title),
                        msgText = "Loading Runners ...",
                        onDismiss = {}
                    )
                }
                is Status.Failure -> {}
                is Status.Success -> { success.value = true }
            }
            if (success.value) {
                OnSuccess(state, onEvent)
            }
        }
    }
}

@Composable
private fun OnSuccess(
    state: RunnersState,
    onEvent: (RunnersEvent) -> Unit
) {
    Header(state)
    Body(state, onEvent)
}

@Composable
private fun Body(
    state: RunnersState,
    onEvent: (RunnersEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding64dp)
    ) {
        items(
            items = processScratchings(state.runners!!),
            key = { it.id }
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
}

@Composable
private fun Header(state: RunnersState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height64dp)
    ) {
        state.race?.let { race ->
            RacesHeader(
                race = race,
                backgroundColour = MaterialTheme.colorScheme.surface,
                borderColour = MaterialTheme.colorScheme.primary
            )
        }
    }
}
/**
 * Sort the list of Runners so that those who are scratched are at the end of the listing.
 * @param runners: The list of Runners returned in the state.
 * @return The modified list.
 */
fun processScratchings(runners: List<Runner>): List<Runner> {
    val lTemp = mutableListOf<Runner>()
    runners.partition { runner ->
        runner.isScratched
    }.also { pair ->
        lTemp.addAll(pair.second)
        lTemp.addAll(pair.first)
    }
    return lTemp
}

fun backNavigate(
    navController: NavController,
    state: RunnersState
) {
    navController.navigate(
        Screens.MeetingsScreen.route + "fromApi=${state.fromApi}"
    ) {
        popUpTo(route = Screens.MeetingsScreen.route) {
            inclusive = true
        }
    }
}
