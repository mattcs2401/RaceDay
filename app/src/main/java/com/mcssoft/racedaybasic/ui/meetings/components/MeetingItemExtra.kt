package com.mcssoft.racedaybasic.ui.meetings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.ui.theme.fontSize10sp
import com.mcssoft.racedaybasic.ui.theme.margin0dp
import com.mcssoft.racedaybasic.ui.theme.margin16dp
import com.mcssoft.racedaybasic.ui.theme.margin8dp

/**
 * Additional display of Meeting details (the 'expanded' state).
 * @param meeting: The Meeting to get details from for display.
 * @param onItemClick: Used for navigation. Return the selected Meeting to the MeetingsScreen.
 */
@Composable
fun MeetingItemExtra(
    meeting: Meeting,
    onItemClick: (Meeting) -> Unit
) {
    ConstraintLayout(
        constraintSet,
        modifier = Modifier
            .padding(top = 48.dp) // simply to give room for the top row.
            .clickable {
                onItemClick(meeting)
            },
    ) {
        Text(
            "Races: ${meeting.racesNo}",
            Modifier.layoutId("idRacesNo"),
            fontSize = fontSize10sp
        )
        meeting.weatherCondition?.let {
            Text(
                "Weather: $it",
                Modifier.layoutId("idWeatherCond"),
                fontSize = fontSize10sp
            )
        }
        meeting.trackCondition?.let {
            Text(
                "Track: $it",
                Modifier.layoutId("idTrackCond"),
                fontSize = fontSize10sp
            )
        }
        Text(
            "Rail: ${meeting.railPosition.toString()}",
            Modifier.layoutId("idRailPos"),
            fontSize = fontSize10sp
        )
    }
}

private val constraintSet = ConstraintSet {
    val idRacesNo = createRefFor("idRacesNo")
    val idWeatherCond = createRefFor("idWeatherCond")
    val idTrackCond = createRefFor("idTrackCond")
    val idRailPos = createRefFor("idRailPos")

    constrain(idRacesNo) {
        start.linkTo(parent.start, margin = margin16dp)
        top.linkTo(parent.top, margin = margin0dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idWeatherCond) {
        start.linkTo(idRacesNo.end, margin = margin16dp)
        top.linkTo(idRacesNo.top, margin = margin0dp)
    }
    constrain(idTrackCond) {
        start.linkTo(idWeatherCond.end, margin = margin8dp)
        top.linkTo(idWeatherCond.top, margin = margin0dp)
    }
    constrain(idRailPos) {
        start.linkTo(idTrackCond.end, margin = margin8dp)
        top.linkTo(idTrackCond.top, margin = margin0dp)
    }
}