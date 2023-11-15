package com.mcssoft.raceday.ui.components.runners.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.ui.components.runners.RunnersEvent
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.fontSize10sp
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin48dp
import com.mcssoft.raceday.ui.theme.margin4dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp

@Composable
fun RunnerItem(
    race: Race,         // for if/when Runner is checked, need Race info to create Summary record.
    runner: Runner,
    onEvent: (RunnersEvent) -> Unit,
    onItemClick: (Runner) -> Unit
){
    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    var scratched by remember { mutableStateOf(false) }

    var textStyle = TextStyle(textDecoration = TextDecoration.None)

    if(runner.isScratched) {
        scratched = true
        textStyle = TextStyle(textDecoration = TextDecoration.LineThrough)
    }

    Card(
        modifier = Modifier
           .fillMaxWidth()
           .padding(padding4dp),
      shape = RoundedCornerShapes.medium,
      elevation = elevation4dp
    ) {
        ConstraintLayout(
            constraintSet,
            if(!scratched) {
                Modifier.clickable { onItemClick(runner) }
//                Modifier.background(color = Color.Red)
//            } else {
//                Modifier.clickable { onItemClick(runner) }
            } else {
//                Modifier.clickable { }
                Modifier.clickable(enabled = false, onClick = {})
            }
        ) {
            Text(
                runner.runnerNumber.toString(),
                Modifier.layoutId("idRunnerNo"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                runner.last5Starts,
                Modifier.layoutId("idLastFive"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                runner.runnerName,
                Modifier.layoutId("idRunnerName"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Text(
                "(${runner.barrierNumber})",
                Modifier.layoutId("idBarrier"),
                fontSize = fontSize10sp
            )
            Text(
                runner.trainerName,
                Modifier.layoutId("idTrainerName"),
                fontSize = fontSize10sp
            )
            Text(
                runner.riderDriverName,
                Modifier.layoutId("idRider"),
                fontSize = fontSize10sp
            )
            if (!scratched) {
                IconButton(
                    onClick = {
                        expandedState = !expandedState
                    },
                    Modifier
                        .layoutId("idArrow")
                        .rotate(rotationState)
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
        }
        if (expandedState) {
            // Runner extra info, the 'expanded' state.
            RunnerItemExtra(race, runner, onEvent)
        }
    }
}

private val constraintSet = ConstraintSet {
    val idRunnerNo = createRefFor("idRunnerNo")
    val idLastFive = createRefFor("idLastFive")
    val idRunnerName = createRefFor("idRunnerName")
    val idBarrier = createRefFor("idBarrier")
    val idTrainerName = createRefFor("idTrainerName")
    val idRider = createRefFor("idRider")
    val idArrow = createRefFor("idArrow")

    constrain(idRunnerNo) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin8dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idLastFive) {
        top.linkTo(idRunnerNo.top, margin = margin0dp)
        start.linkTo(idRunnerNo.end, margin = margin4dp)
    }
    constrain(idRunnerName) {
        top.linkTo(idLastFive.top, margin = margin0dp)
        start.linkTo(idLastFive.end, margin = margin4dp)
    }
    constrain(idBarrier) {
        top.linkTo(idRunnerName.top, margin = margin0dp)
        start.linkTo(idRunnerName.end, margin = margin4dp)
    }
    constrain(idTrainerName) {
        top.linkTo(idBarrier.top, margin = margin0dp)
        start.linkTo(idBarrier.end, margin = margin4dp)
    }
    constrain(idRider) {
        top.linkTo(idTrainerName.top, margin = margin0dp)
        end.linkTo(parent.end, margin = margin48dp)
    }
    constrain(idArrow) {
        end.linkTo(parent.absoluteRight)
        centerVerticallyTo(parent)
    }
}
