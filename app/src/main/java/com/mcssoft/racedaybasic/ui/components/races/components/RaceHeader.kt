package com.mcssoft.racedaybasic.ui.components.races.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.ui.theme.*

@Composable
fun RaceHeader(
    race: Race,
    colour: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colour)
            .border(
                width = width2dp,
                color = Color.Blue
            )
    ){
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (idRaceNo,
                idRaceName,
                idRaceTime,
                idRaceDist) = createRefs()

            /* Top line. */

            Text(
                text = "R${race.raceNo}",
                Modifier.constrainAs(idRaceNo) {
                    top.linkTo(
                        parent.top,
                        margin = margin8dp
                    )
                    start.linkTo(
                        parent.start,
                        margin = margin8dp
                    )
                },
                fontSize = fontSize12sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = race.raceName,
                Modifier.constrainAs(idRaceName) {
                    top.linkTo(
                        idRaceNo.top,
                        margin = margin0dp
                    )
                    start.linkTo(
                        idRaceNo.end,
                        margin = margin16dp
                    )
                },
                fontSize = fontSize12sp
            )
            /* Second line. */
            Text(
                text = race.raceStartTime,
                Modifier.constrainAs(idRaceTime) {
                    top.linkTo(
                        idRaceName.bottom,
                        margin = margin8dp
                    )
                    start.linkTo(
                        idRaceName.start,
                        margin = margin0dp
                    )
                    bottom.linkTo(
                        parent.bottom,
                        margin = margin8dp
                    )
                },
                fontSize = fontSize12sp
            )
            Text(
                text = "${race.raceDistance}m",
                Modifier.constrainAs(idRaceDist) {
                    start.linkTo(
                        idRaceTime.end,
                        margin = margin16dp
                    )
                    top.linkTo(
                        idRaceTime.top,
                        margin = margin0dp
                    )
                },
                fontSize = fontSize12sp
            )
        }
    }
}