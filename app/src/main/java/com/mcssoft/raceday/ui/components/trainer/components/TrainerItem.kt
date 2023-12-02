package com.mcssoft.raceday.ui.components.trainer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
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
import com.mcssoft.raceday.domain.model.Trainer
import com.mcssoft.raceday.ui.components.trainer.TrainerEvent
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin4dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp

@Composable
fun TrainerItem(
    trainer: Trainer,
    onEvent: (TrainerEvent) -> Unit,   // TBA
    onItemClick: (Trainer) -> Unit     // TBA
) {
    val textStyle = TextStyle(textDecoration = TextDecoration.None)

//    var isChecked by remember { mutableStateOf(false) }
//    isChecked = trainer.isChecked

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
                trainer.sellCode,
                Modifier.layoutId("idSellCode"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "R${trainer.raceNumber.toString()}",
                Modifier.layoutId("idRaceNumber"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "H${trainer.runnerNumber.toString()} : ${trainer.runnerName}",
                Modifier.layoutId("idRunnerNumberName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "Jockey: ${trainer.riderDriverName}",
                Modifier.layoutId("idRiderDriverName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "(${trainer.venueMnemonic})",
                Modifier.layoutId("idVenueMnemonic"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                trainer.raceTime,
                Modifier.layoutId("idRaceTime"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                trainer.trainerName,
                Modifier.layoutId("idTrainerName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
//            Checkbox(
//                checked = isChecked,
//                onCheckedChange = { checked ->
//                    isChecked = checked
////                    trainer.isChecked = isChecked
////                    onEvent(TrainersEvent.Check(race, runner))
//                },
//                Modifier.layoutId("idCheckBox"),
//                enabled = true,
//                colors = CheckboxDefaults.colors(
//                    checkedColor = Color.Magenta,
//                    uncheckedColor = Color.Gray
//                )
//            )
        }
    }
}

private val constraintSet = ConstraintSet {
    val idSellCode = createRefFor("idSellCode")
    val idVenueMnemonic = createRefFor("idVenueMnemonic")
    val idRaceNumber = createRefFor("idRaceNumber")
    val idRaceTime = createRefFor("idRaceTime")
    val idRunnerName = createRefFor("idRunnerName")
    val idRunnerNumberName = createRefFor("idRunnerNumberName")
    val idRiderDriverName = createRefFor("idRiderDriverName")
    val idTrainerName = createRefFor("idTrainerName")
//    val idCheckBox = createRefFor("idCheckBox")

    // 1st (top) line.
    constrain(idSellCode) {
        top.linkTo(parent.top, margin = margin8dp)
        start.linkTo(parent.start, margin = margin8dp)
    }
    constrain(idRaceNumber) {
        top.linkTo(idSellCode.top, margin = margin0dp)
        start.linkTo(idSellCode.end, margin = margin4dp)
    }
    constrain(idRunnerNumberName) {
        top.linkTo(idRaceNumber.top, margin = margin0dp)
        start.linkTo(idRaceNumber.end, margin = margin8dp)
    }
    constrain(idRiderDriverName) {
        top.linkTo(idRunnerNumberName.top, margin = margin0dp)
        start.linkTo(idRunnerNumberName.end, margin = margin8dp)
    }
    // 2nd line.
    constrain(idVenueMnemonic) {
        top.linkTo(idSellCode.bottom, margin = margin8dp)
        start.linkTo(idSellCode.start, margin = margin4dp)
        bottom.linkTo(parent.bottom, margin = margin8dp)
    }
    constrain(idRaceTime) {
        top.linkTo(idVenueMnemonic.top, margin = margin0dp)
        start.linkTo(idVenueMnemonic.end, margin = margin8dp)
    }
    constrain(idTrainerName) {
        top.linkTo(idRaceTime.top, margin = margin0dp)
        start.linkTo(idRaceTime.end, margin = margin8dp)
    }
//    constrain(idCheckBox) {
//        top.linkTo(parent.top, margin = margin0dp)
//        end.linkTo(parent.absoluteRight, margin = margin8dp)
//    }
}
/*
    var raceNumber: Int,
    var raceTime: String,
    var runnerName: String,
    var runnerNumber: Int,
    var riderDriverName: String,
    var trainerName: String
 */