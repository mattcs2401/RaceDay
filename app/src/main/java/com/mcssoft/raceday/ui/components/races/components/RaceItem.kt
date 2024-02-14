package com.mcssoft.raceday.ui.components.races.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.R
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.ui.theme.AppShapes
import com.mcssoft.raceday.ui.theme.components.card.lightRaceAbandonedCardColours
import com.mcssoft.raceday.ui.theme.components.card.lightRaceCardColours
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp

@OptIn(ExperimentalFoundationApi::class) // for Long click.
@Composable
fun RaceItem(
    race: Race,
    onItemClick: (Race) -> Unit,
    onItemLongClick: (Race) -> Unit
) {
    val abandoned by remember { mutableStateOf(false) }.also {
        if (race.raceStatus == LocalContext.current.resources.getString(R.string.abandoned)) {
            it.value = true
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(padding4dp)
            .combinedClickable(
                enabled = true,
                onClick = {
                    if(!abandoned) { onItemClick(race) }
                },
                onLongClick = {
                    if(!abandoned) { onItemLongClick(race) }
                }
            ),
        shape = AppShapes.medium,
        colors = if(abandoned) {
            lightRaceAbandonedCardColours
        } else {
               lightRaceCardColours
        },
//        border = if(abandoned) {
//            BorderStroke(width = width2dp, color = MaterialTheme.colorScheme.error)
//        }
    ) {
        Layout(race, abandoned)
    }
}

@Composable
private fun Layout(
    race: Race,
    abandoned: Boolean
) {
    ConstraintLayout(
        constraintSet,
        modifier = Modifier.fillMaxWidth()
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
        if (abandoned) {
            Text(
                race.raceStatus.uppercase(),
                Modifier.layoutId("idAbandoned"),
                fontSize = fontSize12sp,
                color = Color.Red
            )
        }
    }
}

private val constraintSet = ConstraintSet {

    val idRaceNo = createRefFor("idRaceNo")
    val idRaceName = createRefFor("idRaceName")
    val idRaceTime = createRefFor("idRaceTime")
    val idRaceDist = createRefFor("idRaceDist")
    val idRaceClassCond = createRefFor("idRaceClassCond")
    val idAbandoned = createRefFor("idAbandoned")

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
        top.linkTo(idRaceClassCond.top, margin = margin0dp)
        end.linkTo(parent.absoluteRight, margin = margin8dp)
    }
}
