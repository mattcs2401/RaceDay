package com.mcssoft.racedaycompose.ui.meetings.components

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
            .clickable { onItemClick(meeting) },
    ) {
        Text(
            "Races: ${meeting.racesNo}",
            Modifier.layoutId("idRacesNo"),
            fontSize = 12.sp
        )

        meeting.weatherCondition?.let {
            Text(
                it,
                Modifier.layoutId("idWeatherCond"),
                fontSize = 12.sp
            )
        }

        meeting.trackCondition?.let {
            Text(
                it,
                Modifier.layoutId("idTrackCond"),
                fontSize = 12.sp
            )
        }

//        if (meeting .trackRating > 0) {
//            Text(
//                meeting.trackRating.toString(),
//                Modifier.layoutId("idTrackRating"),
//                fontSize = 12.sp
//            )
//        }

    }
}

private val constraintSet = ConstraintSet {
    val idRacesNo = createRefFor("idRacesNo")
    val idWeatherCond = createRefFor("idWeatherCond")
    val idTrackCond = createRefFor("idTrackCond")
    val idTrackRating = createRefFor("idTrackRating")

    constrain(idRacesNo) {
        start.linkTo(parent.start, margin = 16.dp)
        top.linkTo(parent.top, margin = 0.dp)
        bottom.linkTo(parent.bottom, margin = 16.dp)
    }
    constrain(idWeatherCond) {
        start.linkTo(idRacesNo.end, margin = 32.dp)
        top.linkTo(idRacesNo.top, margin = 0.dp)
    }
    constrain(idTrackCond) {
        start.linkTo(idWeatherCond.end, margin = 8.dp)
        top.linkTo(idWeatherCond.top, margin = 0.dp)
    }
    constrain(idTrackRating) {
        start.linkTo(idTrackCond.end, margin = 8.dp)
        top.linkTo(idTrackCond.top, margin = 0.dp)
    }
}