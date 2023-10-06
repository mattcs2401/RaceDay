package com.mcssoft.racedaybasic.ui.components.races.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.ui.theme.fontSize12sp
import com.mcssoft.racedaybasic.ui.theme.margin0dp
import com.mcssoft.racedaybasic.ui.theme.margin16dp
import com.mcssoft.racedaybasic.ui.theme.margin32dp
import com.mcssoft.racedaybasic.ui.theme.margin4dp
import com.mcssoft.racedaybasic.ui.theme.margin8dp
import com.mcssoft.racedaybasic.ui.theme.width2dp

/**
 * Meeting summary information at the top of the list of Races for that Meeting.
 * @param meeting: The Meeting.
 * @param bkgColour: The background colour.
 *
 * From: https://howtodoandroid.com/jetpack-compose-constraintlayout/
 */
@Composable
fun MeetingHeader(
    meeting: Meeting,
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
    ){
        ConstraintLayout(
            constraintSet
        ) {
            Text(
                text = "Venue: ",
                Modifier.layoutId("idVenueName")
            )
            Text(
                text = meeting.meetingName,
                Modifier.layoutId("idVenueNameText")
            )
            Text(
                "Races: ${meeting.racesNo}",
                Modifier.layoutId("idRacesNo"),
                fontSize = fontSize12sp
            )
            meeting.weatherCondition?.let {
                Text(
                    "Weather: $it",
                    Modifier.layoutId("idWeatherCond"),
                    fontSize = fontSize12sp
                )
            }
            meeting.trackCondition?.let {
                Text(
                    "Track: $it",
                    Modifier.layoutId("idTrackCond"),
                    fontSize = fontSize12sp
                )
            }
        }
    }
}

private val constraintSet = ConstraintSet {
    val idVenueName = createRefFor("idVenueName")
    val idVenueNameText = createRefFor("idVenueNameText")
    val idRacesNo = createRefFor("idRacesNo")
    val idWeatherCond = createRefFor("idWeatherCond")
    val idTrackCond = createRefFor("idTrackCond")

    // 1st line layout.
    constrain(idVenueName) {
        top.linkTo(parent.top, margin = margin8dp)
        start.linkTo(parent.start, margin = margin16dp)
    }
    constrain(idVenueNameText) {
        top.linkTo(idVenueName.top, margin = margin0dp)
        start.linkTo(idVenueName.end, margin = margin4dp)
    }
    // 2nd line layout.
    constrain(idRacesNo) {
        start.linkTo(idVenueName.start, margin = margin0dp)
        top.linkTo(idVenueName.bottom, margin = margin8dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }
    constrain(idWeatherCond) {
        start.linkTo(idRacesNo.end, margin = margin32dp)
        top.linkTo(idRacesNo.top, margin = margin0dp)
    }
    constrain(idTrackCond) {
        start.linkTo(idWeatherCond.end, margin = margin8dp)
        top.linkTo(idWeatherCond.top, margin = margin0dp)
    }
}
