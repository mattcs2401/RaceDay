package com.mcssoft.racedaybasic.ui.components.runners.components;

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaybasic.ui.theme.elevation4dp
import com.mcssoft.racedaybasic.ui.theme.padding4dp

@Composable
fun RunnerItem(
        runner: Runner,
        onItemClick: (Runner) -> Unit
){
    Card(
        modifier = Modifier
           .fillMaxWidth()
           .padding(padding4dp),
      shape = RoundedCornerShapes.medium,
      elevation = elevation4dp
      //backgroundColor = TBA
    ) {
        ConstraintLayout(
            constraintset,
            modifier = Modifier.clickable { onItemClick(runner) }
        ) {

        }
    }
}

private val constraintset = ConstraintSet() {

}

