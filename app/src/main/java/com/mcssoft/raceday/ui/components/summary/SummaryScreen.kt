package com.mcssoft.raceday.ui.components.summary

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.CommonDialog
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.summary.SummaryState.Status
import com.mcssoft.raceday.ui.components.summary.components.SummaryItem
import com.mcssoft.raceday.ui.theme.lightTopAppBarColours
import com.mcssoft.raceday.ui.theme.padding64dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SummaryScreen(
    state: SummaryState,
    navController: NavController,
    onEvent: (SummaryEvent) -> Unit
) {
    val showRemoveDialog = remember { mutableStateOf(false) }
    var summaryId: Long = 0

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_summary),
                colours = lightTopAppBarColours,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    backNavigate(navController, state)
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(SummaryEvent.Refresh)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_refresh_24),
                            stringResource(id = R.string.lbl_icon_refresh),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        if (showRemoveDialog.value) {
            ShowRemoveDialog(
                show = showRemoveDialog,
                summaryId = summaryId,
                onEvent = onEvent
            )
        }
    }
    when (state.status) {
        is Status.Initialise -> {}
        is Status.Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_loading_summaries),
                msgText = stringResource(id = R.string.dlg_loading_msg),
                onDismiss = {}
            )
        }
        is Status.Failure -> {}
        is Status.Success -> {
            if (state.summaries.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            top = padding64dp
                        )
                ) {
                    Text(
                        stringResource(id = R.string.nothing_to_show),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                // Summaries listing.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding64dp)
                ) {
                    items(
                        items = state.summaries,
                        key = { it.id }
                    ) { summary ->
                        SummaryItem(
                            summary = summary,
                            onItemClick = {
                                navController.navigate(
                                    Screens.RunnerScreen.route + "runnerId=${summary.runnerId}"
                                )
                            },
                            onItemLongClick = {
                                summaryId = it.id
                                showRemoveDialog.value = true
                            }
                        )
                    }
                }
            }
        }
    }
}

fun backNavigate(
    navController: NavController,
    state: SummaryState
) {
    navController.navigate(
        Screens.MeetingsScreen.route + "fromApi=${state.fromApi}"
    ) {
        popUpTo(route = Screens.MeetingsScreen.route) {
            inclusive = true
        }
    }
}

@Composable
fun ShowRemoveDialog(
    show: MutableState<Boolean>,
    summaryId: Long,
    onEvent: (SummaryEvent) -> Unit
) {
    CommonDialog(
        icon = R.drawable.ic_summary_48,
        dialogTitle = stringResource(id = R.string.dlg_remove_summary_title),
        dialogText = stringResource(id = R.string.dlg_remove_summary_text),
        confirmButtonText = stringResource(id = R.string.lbl_btn_ok),
        dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
        onConfirmClicked = {
            show.value = !show.value
            onEvent(SummaryEvent.Removal(summaryId))
        },
        onDismissClicked = {
            show.value = !show.value
        }
    )
}
