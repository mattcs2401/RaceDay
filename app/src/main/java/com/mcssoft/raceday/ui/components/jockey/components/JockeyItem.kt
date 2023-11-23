package com.mcssoft.raceday.ui.components.jockey.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.ui.components.jockey.JockeyEvent
import com.mcssoft.raceday.ui.components.settings.SettingsEvent
import com.mcssoft.raceday.ui.components.trainer.TrainerEvent
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.padding4dp

@Composable
fun JockeyItem(
//    summary: Summary,
    onEvent: (JockeyEvent) -> Unit,   // TBA
//    onItemClick: (Summary) -> Unit     // TBA
) {
    var textStyle = TextStyle(textDecoration = TextDecoration.None)

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
//            Text(
//                summary.sellCode,//venueMnemonic,
//                Modifier.layoutId("idVenueMnemonic"),
//                fontSize = fontSize12sp,
//                style = textStyle
//            )
//            Text(
//                "R${summary.raceNumber}",
//                Modifier.layoutId("idRaceNumber"),
//                fontSize = fontSize12sp,
//                style = textStyle
//            )
//            Text(
//                "H${summary.runnerNumber.toString()}",
//                Modifier.layoutId("idRunnerNumber"),
//                fontSize = fontSize12sp,
//                style = textStyle
//            )
//            Text(
//                summary.runnerName,
//                Modifier.layoutId("idRunnerName"),
//                fontSize = fontSize12sp,
//                style = textStyle
//            )
//            Text(
//                summary.raceStartTime,
//                Modifier.layoutId("idRaceStartTime"),
//                fontSize = fontSize12sp,
//                style = textStyle
//            )
        }
    }
}

private val constraintSet = ConstraintSet {
//    val idVenueMnemonic = createRefFor("idVenueMnemonic")
//    val idRaceNumber = createRefFor("idRaceNumber")
//    val idRunnerNumber = createRefFor("idRunnerNumber")
//    val idRunnerName = createRefFor("idRunnerName")
//    val idRaceStartTime = createRefFor("idRaceStartTime")
//    // TODO - this is just 1st cut, will likely need tweaking.
//    constrain(idVenueMnemonic) {
//        top.linkTo(parent.top, margin = margin16dp)
//        start.linkTo(parent.start, margin = margin8dp)
//        bottom.linkTo(parent.bottom, margin = margin16dp)
//    }
//    constrain(idRaceNumber) {
//        top.linkTo(idVenueMnemonic.top, margin = margin0dp)
//        start.linkTo(idVenueMnemonic.end, margin = margin4dp)
//    }
//    constrain(idRunnerNumber) {
//        top.linkTo(idRaceNumber.top, margin = margin0dp)
//        start.linkTo(idRaceNumber.end, margin = margin4dp)
//    }
//    constrain(idRunnerName) {
//        top.linkTo(idRunnerNumber.top, margin = margin0dp)
//        start.linkTo(idRunnerNumber.end, margin = margin4dp)
//    }
//    constrain(idRaceStartTime) {
//        top.linkTo(idRunnerName.top, margin = margin0dp)
//        start.linkTo(idRunnerName.end, margin = margin4dp)
//    }
}
