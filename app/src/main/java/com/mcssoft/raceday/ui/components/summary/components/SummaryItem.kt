package com.mcssoft.raceday.ui.components.summary.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.ui.components.summary.SummaryEvent
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin4dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp

@Composable
fun SummaryItem(
    summary: Summary,
    onEvent: (SummaryEvent) -> Unit,   // TBA
    onItemClick: (Summary) -> Unit     // TBA
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = RoundedCornerShapes.medium,
        elevation = elevation4dp
    ) {
        ConstraintLayout(
            constraintSet
        ){
            Text(
                summary.venueMnemonic,
                Modifier.layoutId("idVenueMnemonic"),
                fontSize = fontSize12sp
            )
            Text(
                summary.raceNumber.toString(),
                Modifier.layoutId("idRaceNumber"),
                fontSize = fontSize12sp
            )
            Text(
                summary.runnerNumber.toString(),
                Modifier.layoutId("idRunnerNumber"),
                fontSize = fontSize12sp
            )
            Text(
                summary.runnerName,
                Modifier.layoutId("idRunnerName"),
                fontSize = fontSize12sp
            )
            Text(
                summary.raceStartTime,
                Modifier.layoutId("idRaceStartTime"),
                fontSize = fontSize12sp
            )
        }
    }
}

private val constraintSet = ConstraintSet {
    val idVenueMnemonic = createRefFor("idVenueMnemonic")
    val idRaceNumber = createRefFor("idRaceNumber")
    val idRunnerNumber = createRefFor("idRunnerNumber")
    val idRunnerName = createRefFor("idRunnerName")
    val idRaceStartTime = createRefFor("idRaceStartTime")
    // TODO - this is just 1st cut, will likely need tweaking.
    constrain(idVenueMnemonic) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin8dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idRaceNumber) {
        top.linkTo(idVenueMnemonic.top, margin = margin0dp)
        start.linkTo(idVenueMnemonic.end, margin = margin4dp)
    }
    constrain(idRunnerNumber) {
        top.linkTo(idRaceNumber.top, margin = margin0dp)
        start.linkTo(idRaceNumber.end, margin = margin4dp)
    }
    constrain(idRunnerName) {
        top.linkTo(idRunnerNumber.top, margin = margin0dp)
        start.linkTo(idRunnerNumber.end, margin = margin4dp)
    }
    constrain(idRaceStartTime) {
        top.linkTo(idRunnerName.top, margin = margin0dp)
        start.linkTo(idRunnerName.end, margin = margin4dp)
    }
}
/*
    var venueMnemonic: String,    // e.g. BR (from Race).
    var raceNumber: Int,          // e.g. 1 (from Race).
    var runnerNumber: Int,        // e.g. 2 (from Runner).
    var runnerName: String,       // e.g. "name" (from Runner).
    var raceStartTime: String     // e.g. 12:30 (from Race).
 */