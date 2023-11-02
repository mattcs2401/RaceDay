package com.mcssoft.raceday.ui.components.runners.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.width2dp
import com.mcssoft.raceday.utility.Constants

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
            Text(
                race.raceNumber.toString(),
                Modifier.layoutId("idRaceNo"),
                //fontSize = fontSize16sp
            )
            val name = race.raceName
            if(name.length > Constants.TAKE) {
                Text(
                    name.take(Constants.TAKE) + " ...",
                    Modifier.layoutId("idRaceName"),
                    //fontSize = fontSize16sp
                )
            } else {
                Text(
                    name,
                    Modifier.layoutId("idRaceName"),
                    //fontSize = fontSize16sp
                )
            }
        Text(
            "Start ${race.raceStartTime}",
            Modifier.layoutId("idRaceTime"),
            fontSize = fontSize12sp
        )
        Text(
            "Distance ${race.raceDistance}m",
            Modifier.layoutId("idRaceDist"),
            fontSize = fontSize12sp
        )
        }
    }
}

private val constraintSet = ConstraintSet {
    val idRaceNo = createRefFor("idRaceNo")
    val idRaceName = createRefFor("idRaceName")
    val idRaceTime = createRefFor("idRaceTime")
    val idRaceDist = createRefFor("idRaceDist")

    constrain(idRaceNo) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin16dp)
    }
    constrain(idRaceName) {
        top.linkTo(idRaceNo.top, margin = margin0dp)
        start.linkTo(idRaceNo.end, margin = margin8dp)
    }
    constrain(idRaceTime) {
        top.linkTo(idRaceName.bottom, margin = margin8dp)
        start.linkTo(idRaceName.start, margin = margin0dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idRaceDist) {
        top.linkTo(idRaceTime.top, margin = margin0dp)
        start.linkTo(idRaceTime.end, margin = margin16dp)
    }
}
