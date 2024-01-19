package com.mcssoft.raceday.ui.components.summary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.R
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.borderStroke
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.fontSize14sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin4dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp
import com.mcssoft.raceday.utility.Constants

@Composable
fun SummaryItem(
    summary: Summary,
    onItemClick: (Summary) -> Unit
) {
    val textStyle = TextStyle(textDecoration = TextDecoration.None)

    // TODO - refine these colours.
    val backgroundColour = if (summary.isPastRaceTime) {
        colorResource(id = R.color.colourAccent)
    } else {
        colorResource(id = R.color.colourAccentComp)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = RoundedCornerShapes.medium,
        elevation = elevation4dp,
        backgroundColor = backgroundColour,
        border = borderStroke
    ) {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.clickable {
                onItemClick(summary)
            }
        ){
            Text(
                summary.sellCode,
                Modifier.layoutId("idSellCode"),
                fontSize = fontSize14sp,
                style = textStyle
            )
            Text(
                "${summary.raceNumber}",
                Modifier.layoutId("idRaceNumber"),
                fontSize = fontSize14sp,
                style = textStyle
            )
            Text(
                "H${summary.runnerNumber}",
                Modifier.layoutId("idRunnerNumber"),
                fontSize = fontSize14sp,
                style = textStyle
            )
            Text(
                summary.runnerName,
                Modifier.layoutId("idRunnerName"),
                fontSize = fontSize14sp,
                style = textStyle
            )
            Text(
                summary.raceStartTime,
                Modifier.layoutId("idRaceStartTime"),
                fontSize = fontSize14sp,
                style = textStyle
            )
            // 2nd row.
            Text(
                summary.venueMnemonic,
                Modifier.layoutId("idVenueMnemonic"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "(J) ${summary.riderDriverName}",
                Modifier.layoutId("idRiderDriverName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            var trainer = summary.trainerName
            if (trainer.length > Constants.TRAINER_MAX) {
                trainer = "${trainer.take(Constants.TRAINER_TAKE)}..."
            }
            Text(
                "(T) $trainer",
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
    val idVenueMnemonic = createRefFor("idVenueMnemonic")
    val idRiderDriverName = createRefFor("idRiderDriverName")
    val idTrainerName = createRefFor("idTrainerName")

    constrain(idSellCode) {
        top.linkTo(parent.top, margin = margin8dp)
        start.linkTo(parent.start, margin = margin8dp)
    }
    constrain(idRaceNumber) {
        top.linkTo(idSellCode.top, margin = margin0dp)
        start.linkTo(idSellCode.end, margin = margin4dp)
    }
    constrain(idRunnerNumber) {
        top.linkTo(idRaceNumber.top, margin = margin0dp)
        start.linkTo(idRaceNumber.end, margin = margin8dp)
    }
    constrain(idRunnerName) {
        top.linkTo(idRunnerNumber.top, margin = margin0dp)
        start.linkTo(idRunnerNumber.end, margin = margin8dp)
    }
    constrain(idRaceStartTime) {
        top.linkTo(idRunnerName.top, margin = margin0dp)
        end.linkTo(parent.absoluteRight, margin = margin16dp)
    }
    // 2nd row.
    constrain(idVenueMnemonic) {
        start.linkTo(parent.start, margin8dp)
        top.linkTo(idSellCode.bottom, margin8dp)
        bottom.linkTo(parent.bottom, margin8dp)
    }
    constrain(idRiderDriverName) {
        start.linkTo(idVenueMnemonic.end, margin8dp)
        top.linkTo(idVenueMnemonic.top, margin0dp)
    }
    constrain(idTrainerName) {
        top.linkTo(idRiderDriverName.top, margin0dp)
        start.linkTo(idRiderDriverName.end, margin8dp)
    }
}
