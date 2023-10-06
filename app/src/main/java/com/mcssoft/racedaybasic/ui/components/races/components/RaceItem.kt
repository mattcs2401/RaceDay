package com.mcssoft.racedaybasic.ui.components.races.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
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
            modifier = Modifier.clickable { onItemClick(race) }
        ) {
            Text(
                race.raceNo.toString(),
                Modifier.layoutId("idRaceNo"),
                fontSize = fontSize12sp
            )
            Text(
                race.raceName,
                Modifier.layoutId("idRaceName"),
                fontSize = fontSize12sp
            )
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