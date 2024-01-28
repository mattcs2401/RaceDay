package com.mcssoft.raceday.ui.components.runner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.dialog.LoadingDialog
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.runner.RunnerState.Status
import com.mcssoft.raceday.ui.theme.fontSize14sp
import com.mcssoft.raceday.ui.theme.padding8dp
import com.mcssoft.raceday.ui.theme.sixty7Percent
import com.mcssoft.raceday.ui.theme.thirty3Percent

/**
 * @param state: Runner state.
 */
@Composable
fun RunnerScreen(
    state: RunnerState
) {
    val scaffoldState = rememberScaffoldState()
//    val textStyle = TextStyle(textDecoration = TextDecoration.None)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_runner),
                backgroundColour = MaterialTheme.colors.primary,
                actions = { /*TBA*/ }
            )
        }
    ) {
        when (state.status) {
            is Status.Loading -> {
                LoadingDialog(
                    titleText = stringResource(id = R.string.dlg_loading_runner),
                    msgText = stringResource(id = R.string.dlg_loading_msg)
                )
            }
            is Status.Failure -> { /*TBA*/ }
            is Status.Success -> {
                // Runner detail.
                val runner = state.runner
                Box(
                    Modifier.fillMaxSize()
                        .background(MaterialTheme.colors.secondary)
                ) {
                    Column(
                        Modifier.padding(padding8dp)
                    ) {
                        BuildRow(label = "Number:", value = "${runner!!.runnerNumber}")
                        BuildRow(label = "Name:", value = runner.runnerName)
                        BuildRow(label = "Barrier:", value = "${runner.barrierNumber}")
                        BuildRow(label = "TCDW:", value = runner.tcdwIndicators)
                        BuildRow(label = "L5S:", value = runner.last5Starts)
                        BuildRow(label = "Jockey:", value = runner.riderDriverName)
                        BuildRow(label = "Jockey Name:", value = runner.riderDriverFullName)
                        BuildRow(label = "Trainer:", value = runner.trainerName)
                        BuildRow(label = "Trainer Name:", value = runner.trainerFullName)
                        BuildRow(label = "Form:", value = "${runner.dfsFormRating}")
                        BuildRow(label = "Weight:", value = "${runner.handicapWeight}")
                    }
                }
            }
            else -> {}
        }
    }
}

/**
 * Build a Row with two Columns.
 * @param label: Text to display in the left hand column.
 * @param value: Value to display in the right hand column.
 */
@Composable
fun BuildRow(label: String, value: String) {
    Row {
        // Left column.
        Column(
            Modifier
                .padding(8.dp)
                .weight(thirty3Percent)
        ) {
            Text(
                label,
                fontSize = fontSize14sp
            )
        }
        // Right column.
        Column(
            Modifier
                .padding(8.dp)
                .weight(sixty7Percent)
        ) {
            Text(
                value,
                fontSize = fontSize14sp
            )
        }
    }
}
/*
    var runnerName: String,
    var runnerNumber: Int,
    var barrierNumber: Int,
    var tcdwIndicators: String,
    var last5Starts: String,
    var riderDriverName: String,
    var trainerName: String,
    var trainerFullName: String,
    var dfsFormRating: Int,
    var handicapWeight: Double,
 */
