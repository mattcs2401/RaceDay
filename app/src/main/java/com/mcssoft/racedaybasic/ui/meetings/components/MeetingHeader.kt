package com.mcssoft.racedaybasic.ui.meetings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.ui.theme.fontSize12sp
import com.mcssoft.racedaybasic.ui.theme.width2dp

/**
 * Meeting summary information at the top of the list of Races for that Meeting.
 * @param meeting: The Meeting.
 * @param colour: The background colour.
 *
 * From: https://howtodoandroid.com/jetpack-compose-constraintlayout/
 */
@Composable
fun MeetingHeader(
    meeting: Meeting,
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
            constraintSet
        ) {
            Text(
                text = "Meeting: ",
                Modifier.layoutId("idMtgCode")
            )
            Text(
                text = meeting.venueMnemonic,
                Modifier.layoutId("idMtgCodeText")
            )
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
    // 1st line refs.
    val idMtgCode = createRefFor("idMtgCode")
    val idMtgCodeText = createRefFor("idMtgCodeText")
    val idVenueName = createRefFor("idVenueName")
    val idVenueNameText = createRefFor("idVenueNameText")
    // 2nd line refs.
    val idRacesNo = createRefFor("idRacesNo")
    val idWeatherCond = createRefFor("idWeatherCond")
    val idTrackCond = createRefFor("idTrackCond")

    // 1st line layout.
    constrain(idMtgCode) {
        top.linkTo(parent.top, margin = 8.dp)
        start.linkTo(parent.start, margin = 16.dp)
    }
    constrain(idMtgCodeText) {
        top.linkTo(idMtgCode.top, margin = 0.dp)
        start.linkTo(idMtgCode.end, margin = 4.dp)
    }
    constrain(idVenueName) {
        top.linkTo(idMtgCodeText.top, margin = 0.dp)
        start.linkTo(idMtgCodeText.end, margin = 16.dp)
    }
    constrain(idVenueNameText) {
        top.linkTo(idVenueName.top, margin = 0.dp)
        start.linkTo(idVenueName.end, margin = 4.dp)
    }

    // 2nd line layout.
    constrain(idRacesNo) {
        start.linkTo(idMtgCode.start, margin = 0.dp)
        top.linkTo(idMtgCode.bottom, margin = 8.dp)
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
}
