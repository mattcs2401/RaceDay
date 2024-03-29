package com.mcssoft.raceday.ui.components.summary.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.ui.components.summary.SummaryEvent
import com.mcssoft.raceday.ui.theme.AppShapes
import com.mcssoft.raceday.ui.theme.borderStroke
import com.mcssoft.raceday.ui.theme.components.card.lightSummaryCurrentCardColours
import com.mcssoft.raceday.ui.theme.components.card.lightSummaryPreviousCardColours
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.fontSize14sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin4dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp
import com.mcssoft.raceday.ui.theme.padding64dp
import com.mcssoft.raceday.utility.Constants

@OptIn(ExperimentalFoundationApi::class) // for Long click.
@Composable
fun SummaryItem(
    summary: Summary,
    onItemClick: (Summary) -> Unit,
    onItemLongClick: (Summary) -> Unit,
    onEvent: (SummaryEvent) -> Unit // basically for isWagered checkbox.
) {
    val textStyle = TextStyle(textDecoration = TextDecoration.None)

    val isPastRaceTime by remember { mutableStateOf(summary.isPastRaceTime) }

    var isChecked by remember { mutableStateOf(summary.isWagered) }

    // TODO - refine these colours.
    val summaryItemCardColours = if (isPastRaceTime) {
        lightSummaryPreviousCardColours
    } else {
        lightSummaryCurrentCardColours
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp)
        .combinedClickable(
            enabled = true,
            onClick = { onItemClick(summary) },
            onLongClick = { onItemLongClick(summary) },
        ),
        shape = AppShapes.medium,
        colors = summaryItemCardColours,
        border = borderStroke
    ) {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxWidth()
        ) {
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
            var trainerName = summary.trainerName
            if (trainerName.length > Constants.TRAINER_MAX) {
                trainerName = "${trainerName.take(Constants.TRAINER_TAKE)}..."
            }
            Text(
                "(T) $trainerName",
                Modifier.layoutId("idTrainerName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Checkbox(
                checked = isChecked,
                onCheckedChange = { checked ->
                    isChecked = checked
                    summary.isWagered = isChecked
                    onEvent(SummaryEvent.Check(summary))
                },
                Modifier.layoutId("idCheckBox"),
                enabled = !isPastRaceTime,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Magenta,
                    uncheckedColor = Color.Gray
                )
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
    val idCheckBox = createRefFor("idCheckBox")

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
        end.linkTo(parent.absoluteRight, margin = padding64dp)
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
    constrain(idCheckBox) {
        top.linkTo(parent.top, margin = margin0dp)
        end.linkTo(parent.absoluteRight, margin = margin8dp)
    }
}
