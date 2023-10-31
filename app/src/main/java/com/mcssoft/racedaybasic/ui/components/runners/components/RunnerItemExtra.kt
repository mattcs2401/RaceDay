package com.mcssoft.racedaybasic.ui.components.runners.components

import androidx.compose.foundation.layout.padding
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.ui.components.runners.RunnersEvent
import com.mcssoft.racedaybasic.ui.theme.fontSize10sp
import com.mcssoft.racedaybasic.ui.theme.margin0dp
import com.mcssoft.racedaybasic.ui.theme.margin16dp
import com.mcssoft.racedaybasic.ui.theme.margin8dp
import com.mcssoft.racedaybasic.ui.theme.padding32dp

@Composable
fun RunnerItemExtra(
    runner: Runner,
    onEvent: (RunnersEvent) -> Unit,
){

    var isChecked by remember { mutableStateOf(false) }
    isChecked = runner.isChecked

    ConstraintLayout(
        constraintSet,
        modifier = Modifier
            .padding(top = padding32dp) // simply to give room for the top row.
    ) {
        Text(
       "(${runner.tcdwIndicators})",
            Modifier.layoutId("idTcdwIndicators"),
            fontSize = fontSize10sp
        )
        Text(
            "Trainer: ${runner.trainerFullName}",
            Modifier.layoutId("idTrainerName"),
            fontSize = fontSize10sp
        )
        Text(
            "Rtg: ${runner.dfsFormRating}",
            Modifier.layoutId("idDfsFormRating"),
            fontSize = fontSize10sp
        )
        Text(
            "Wgt: ${runner.handicapWeight}",
            Modifier.layoutId("idHandicapWeight"),
            fontSize = fontSize10sp
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                runner.isChecked = isChecked
                onEvent(RunnersEvent.Check(runner))
            },
            Modifier.layoutId("idCheckBox"),
            enabled = true,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Magenta,
                uncheckedColor = Color.Gray
            )
        )
    }
}

private val constraintSet = ConstraintSet() {
    val idTcdwIndicators = createRefFor("idTcdwIndicators")
    val idTrainerName = createRefFor("idTrainerName")
    val idDfsFormRating = createRefFor("idDfsFormRating")
    val idHandicapWeight = createRefFor("idHandicapWeight")
    val idCheckBox = createRefFor("idCheckBox")

    constrain(idTcdwIndicators) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin16dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idTrainerName) {
        top.linkTo(idTcdwIndicators.top, margin = margin0dp)
        start.linkTo(idTcdwIndicators.end, margin = margin8dp)
    }
    constrain(idDfsFormRating) {
        top.linkTo(idTrainerName.top, margin = margin0dp)
        start.linkTo(idTrainerName.end, margin = margin8dp)
    }
    constrain(idHandicapWeight) {
        top.linkTo(idDfsFormRating.top, margin = margin0dp)
        start.linkTo(idDfsFormRating.end, margin = margin8dp)
    }
    constrain(idCheckBox) {
        top.linkTo(parent.top, margin = margin0dp)
        end.linkTo(parent.absoluteRight, margin = margin8dp)
    }
}



