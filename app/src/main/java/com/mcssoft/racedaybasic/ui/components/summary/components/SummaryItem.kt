package com.mcssoft.racedaybasic.ui.components.summary.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaybasic.domain.model.Summary
import com.mcssoft.racedaybasic.ui.components.summary.SummaryEvent
import com.mcssoft.racedaybasic.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaybasic.ui.theme.elevation4dp
import com.mcssoft.racedaybasic.ui.theme.padding4dp

@Composable
fun SummaryItem(
    summary: Summary,
    onEvent: (SummaryEvent) -> Unit,
    onItemClick: (Summary) -> Unit
) {
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

        }
    }
}

private val constraintSet = ConstraintSet {

}