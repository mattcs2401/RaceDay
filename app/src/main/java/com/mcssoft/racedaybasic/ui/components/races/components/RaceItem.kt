package com.mcssoft.racedaybasic.ui.components.races.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toUpperCase
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaybasic.ui.theme.elevation4dp
import com.mcssoft.racedaybasic.ui.theme.fontSize12sp
import com.mcssoft.racedaybasic.ui.theme.margin0dp
import com.mcssoft.racedaybasic.ui.theme.margin16dp
import com.mcssoft.racedaybasic.ui.theme.margin8dp
import com.mcssoft.racedaybasic.ui.theme.padding4dp

@Composable
fun RaceItem(
    race: Race,
    onItemClick: (Race) -> Unit
) {
    var abandoned by remember { mutableStateOf(false) }

    if(race.raceStatus == "Abandoned") {
        abandoned = true
    }

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
            modifier = Modifier.clickable {
                if(!abandoned) {
                    onItemClick(race)
                }
            }
        ) {
            Text(
                race.raceNumber.toString(),
                Modifier.layoutId("idRaceNo"),
                fontSize = fontSize12sp
            )
            Text(
                race.raceName,
                Modifier.layoutId("idRaceName"),
                fontSize = fontSize12sp
            )
            Text(
                "Time: ${race.raceStartTime}",
                Modifier.layoutId("idRaceTime"),
                fontSize = fontSize12sp
            )
            Text(
                "Dist: ${race.raceDistance}m",
                Modifier.layoutId("idRaceDist"),
                fontSize = fontSize12sp
            )
            race.raceClassConditions?.let { rcc ->
                Text(
                    "CC: $rcc",
                    Modifier.layoutId("idRaceClassCond"),
                    fontSize = fontSize12sp
                )
            }
            if(abandoned) {
                Text(
                    race.raceStatus.uppercase(),
                    Modifier.layoutId("idAbandoned"),
                    fontSize = fontSize12sp,
                    color = Color.Red
                )
            }
        }
    }
}

private val constraintSet = ConstraintSet {

    val idRaceNo = createRefFor("idRaceNo")
    val idRaceName = createRefFor("idRaceName")
    val idRaceTime = createRefFor("idRaceTime")
    val idRaceDist = createRefFor("idRaceDist")
    val idAbandoned = createRefFor("idAbandoned")
    val idRaceClassCond = createRefFor("idRaceClassCond")

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
        start.linkTo(parent.start, margin = margin16dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idRaceDist) {
        top.linkTo(idRaceTime.top, margin = margin0dp)
        start.linkTo(idRaceTime.end, margin = margin8dp)
    }
    constrain(idRaceClassCond) {
        top.linkTo(idRaceDist.top, margin = margin0dp)
        start.linkTo(idRaceDist.end, margin = margin8dp)
    }
    constrain(idAbandoned) {
        top.linkTo(idRaceDist.top, margin = margin0dp)
        end.linkTo(parent.absoluteRight, margin = margin8dp)
    }
}