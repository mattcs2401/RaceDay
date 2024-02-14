package com.mcssoft.raceday.ui.components.runners.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.mcssoft.raceday.ui.theme.AppShapes
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.fontSize10sp
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin48dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp
import com.mcssoft.raceday.utility.Constants

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

    var textStyle = TextStyle(textDecoration = TextDecoration.None)

    val scratched by remember { mutableStateOf(false) }.also {
        if(runner.isScratched) {
            it.value = true
            textStyle = TextStyle(textDecoration = TextDecoration.LineThrough)
        }
    }

    val modifier: Modifier = Modifier

    Card(
        modifier
           .fillMaxWidth()
           .padding(padding4dp),
        shape = AppShapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation4dp)
    ) {
        ConstraintLayout(
            constraintSet,
            modifier.fillMaxWidth()
        ) {
            if(!scratched) {
                modifier.clickable { onItemClick(runner) }
            } else {
                modifier.clickable(enabled = false, onClick = {})
            }
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
            if(!runner.isScratched) {
                var riderDriverName = runner.riderDriverName
                if(riderDriverName.length > Constants.JOCKEY_MAX) {
                    riderDriverName = "${riderDriverName.take(Constants.JOCKEY_TAKE)}..."
                }
                Text(
                    riderDriverName,
                    Modifier.layoutId("idRider"),
                    fontSize = fontSize10sp
                )
            } else {
                Text(
                "N/A",
                    Modifier.layoutId("idArrow")     // move to right.
                    .padding(end = margin16dp),
                    fontSize = fontSize10sp
                )
            }
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
//    val idTrainerName = createRefFor("idTrainerName")
    val idRider = createRefFor("idRider")
    val idArrow = createRefFor("idArrow")

    constrain(idRunnerNo) {
        top.linkTo(parent.top, margin = margin8dp)
        start.linkTo(parent.start, margin = margin16dp)
        bottom.linkTo(parent.bottom, margin = margin8dp)
    }
    constrain(idLastFive) {
        top.linkTo(idRunnerNo.top, margin = margin0dp)
        start.linkTo(idRunnerNo.end, margin = margin16dp)
    }
    constrain(idRunnerName) {
        top.linkTo(idLastFive.top, margin = margin0dp)
        start.linkTo(idLastFive.end, margin = margin8dp)
    }
    constrain(idBarrier) {
        top.linkTo(idRunnerName.top, margin = margin0dp)
        start.linkTo(idRunnerName.end, margin = margin8dp)
    }
//    constrain(idTrainerName) {
//        top.linkTo(idBarrier.top, margin = margin0dp)
//        start.linkTo(idBarrier.end, margin = margin4dp)
//    }
    constrain(idRider) {
        top.linkTo(idBarrier.top, margin = margin0dp)
        end.linkTo(parent.end, margin = margin48dp)
    }
    constrain(idArrow) {
        end.linkTo(parent.absoluteRight)
        centerVerticallyTo(parent)
    }
}
