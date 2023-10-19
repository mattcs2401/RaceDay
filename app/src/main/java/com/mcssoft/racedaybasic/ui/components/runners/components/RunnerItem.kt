package com.mcssoft.racedaybasic.ui.components.runners.components;

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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaybasic.ui.theme.elevation4dp
import com.mcssoft.racedaybasic.ui.theme.fontSize10sp
import com.mcssoft.racedaybasic.ui.theme.fontSize12sp
import com.mcssoft.racedaybasic.ui.theme.margin0dp
import com.mcssoft.racedaybasic.ui.theme.margin16dp
import com.mcssoft.racedaybasic.ui.theme.margin48dp
import com.mcssoft.racedaybasic.ui.theme.margin4dp
import com.mcssoft.racedaybasic.ui.theme.margin8dp
import com.mcssoft.racedaybasic.ui.theme.padding4dp

@Composable
fun RunnerItem(
        runner: Runner,
        onItemClick: (Runner) -> Unit
){
    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    Card(
        modifier = Modifier
           .fillMaxWidth()
           .padding(padding4dp),
      shape = RoundedCornerShapes.medium,
      elevation = elevation4dp
      //backgroundColor = TBA
    ) {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.clickable { onItemClick(runner) }
        ) {
            Text(
                runner.runnerNumber.toString(),
                Modifier.layoutId("idRunnerNo"),
                fontSize = fontSize12sp
            )
            Text(
                runner.last5Starts,
                Modifier.layoutId("idLastFive"),
                fontSize = fontSize12sp
            )
            Text(
                runner.runnerName,
                Modifier.layoutId("idRunnerName"),
                fontSize = fontSize12sp
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
            IconButton(
                onClick = {
                    expandedState = !expandedState
                },
                Modifier
                    .layoutId("idArrow")
                    .rotate(rotationState)
            ) {
//                if (!meeting.abandoned) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow"
                )
//                }
            }
        }
        if (expandedState) {
            // Meeting extra info, the 'expanded' state.
            RunnerItemExtra(runner, onItemClick)
        }
    }
}

private val constraintSet = ConstraintSet() {
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

