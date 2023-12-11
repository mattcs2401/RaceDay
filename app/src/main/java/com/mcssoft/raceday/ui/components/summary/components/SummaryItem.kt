package com.mcssoft.raceday.ui.components.summary.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.R
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin4dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp

@Composable
fun SummaryItem(
    summary: Summary,
    onItemClick: (Summary) -> Unit = {}     // TBA
) {
    val textStyle = TextStyle(textDecoration = TextDecoration.None)

    // TODO - refine these colours.
    val backgroundColour = if (summary.isPastRaceTime) {
        colorResource(id = R.color.colourAccent)
    } else {
        colorResource(id = R.color.colourPrimary)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = RoundedCornerShapes.medium,
        elevation = elevation4dp,
        //backgroundColor = backgroundColour
    ) {
        ConstraintLayout(
            constraintSet
        ){
            Text(
                summary.sellCode,
                Modifier.layoutId("idSellCode"),
                color = backgroundColour,
                fontSize = fontSize12sp,
                fontWeight = FontWeight.Bold,
                style = textStyle
            )
            Text(
                "R${summary.raceNumber}",
                Modifier.layoutId("idRaceNumber"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "H${summary.runnerNumber}",
                Modifier.layoutId("idRunnerNumber"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                summary.runnerName,
                Modifier.layoutId("idRunnerName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                summary.raceStartTime,
                Modifier.layoutId("idRaceStartTime"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "(J) ${summary.riderDriverName}",
                Modifier.layoutId("idRiderDriverName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "(T) ${summary.trainerName}",
                Modifier.layoutId("idTrainerName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
        }
    }
}

private val constraintSet = ConstraintSet {
    val idSellCode = createRefFor("idSellCode")
    val idRaceNumber = createRefFor("idRaceNumber")
    val idRunnerNumber = createRefFor("idRunnerNumber")
    val idRunnerName = createRefFor("idRunnerName")
    val idRaceStartTime = createRefFor("idRaceStartTime")
    val idRiderDriverName = createRefFor("idRiderDriverName")
    val idTrainerName = createRefFor("idTrainerName")

    constrain(idSellCode) {
        top.linkTo(parent.top, margin = margin8dp)
        start.linkTo(parent.start, margin = margin8dp)
//        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idRaceNumber) {
        top.linkTo(idSellCode.top, margin = margin0dp)
        start.linkTo(idSellCode.end, margin = margin4dp)
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
    constrain(idRiderDriverName) {
        start.linkTo(parent.start, margin8dp)
        top.linkTo(idSellCode.bottom, margin8dp)
        bottom.linkTo(parent.bottom, margin8dp)
    }
    constrain(idTrainerName) {
        top.linkTo(idRiderDriverName.top, margin0dp)
        start.linkTo(idRiderDriverName.end, margin8dp)
    }
}
/*
    var venueMnemonic: String,    // e.g. BR (from Race).
    var raceNumber: Int,          // e.g. 1 (from Race).
    var runnerNumber: Int,        // e.g. 2 (from Runner).
    var runnerName: String,       // e.g. "name" (from Runner).
    var raceStartTime: String     // e.g. 12:30 (from Race).
 */