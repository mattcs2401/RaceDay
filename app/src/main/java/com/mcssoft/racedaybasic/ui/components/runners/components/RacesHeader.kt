package com.mcssoft.racedaybasic.ui.components.runners.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.ui.theme.width2dp

class RacesHeader /**
 * Race summary information at the top of the list of Runners for that Race.
 * @param race: The Race.
 * @param bkgColour: The background colour.
 *
 * From: https://howtodoandroid.com/jetpack-compose-constraintlayout/
 */
@Composable
fun RacesHeader(
    race: Race,
    bkgColour: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bkgColour)
            .border(
                width = width2dp,
                color = Color.Blue
            )
    ) {
        ConstraintLayout(
            constraintSet
        ) {
        }

    }
}

    private val constraintSet = ConstraintSet {
    }
