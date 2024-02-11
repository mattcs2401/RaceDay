package com.mcssoft.raceday.ui.components.runner

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.runner.RunnerState.Status
import com.mcssoft.raceday.ui.theme.fontSize14sp
import com.mcssoft.raceday.ui.theme.padding64dp
import com.mcssoft.raceday.ui.theme.padding8dp
import com.mcssoft.raceday.ui.theme.sixty7Percent
import com.mcssoft.raceday.ui.theme.thirty3Percent
import com.mcssoft.raceday.ui.theme.topappbar.lightRunnerTopAppBarColours

/**
 * @param state: Runner state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RunnerScreen(
    state: RunnerState,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(content = {
                        Text(
                            stringResource(id = R.string.label_runner),
                            modifier = Modifier.weight(weight = 2f),
                        )
                    })
                },
                colors = lightRunnerTopAppBarColours,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            backNavigate(navController)
                        }
                    ) {
                        Icon(
                            painterResource( id = R.drawable.ic_arrow_back_24 ),
                            stringResource(id = R.string.label_runner)
                        )
                    }
                },
                actions = {}
            )
        }
    ) {
        when (state.status) {
            is Status.Failure -> { /*TBA*/ }
            is Status.Success -> {
                // Runner detail.
                val runner = state.runner
                Box(
                    Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(top = padding64dp)
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

fun backNavigate(
    navController: NavController
) {
    navController.navigate(
        Screens.SummaryScreen.route
    ) {
        popUpTo(route = Screens.SummaryScreen.route) {
            inclusive = true
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
