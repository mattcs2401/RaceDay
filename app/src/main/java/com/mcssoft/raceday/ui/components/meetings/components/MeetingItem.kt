package com.mcssoft.raceday.ui.components.meetings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.ui.theme.AppShapes
import com.mcssoft.raceday.ui.theme.components.card.lightMeetingCardColours
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.Constants.THREE_HUNDRED

@OptIn(ExperimentalFoundationApi::class) // for Long click.
@Composable
fun MeetingItem(
    meeting: Meeting,
    onItemClick: (Meeting) -> Unit,
    onItemLongClick: (Meeting) -> Unit
) {
    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f,
        label = "Expand"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = THREE_HUNDRED,
                    easing = LinearOutSlowInEasing
                )
            )
            .combinedClickable(
                enabled = true,
                onClick = {
                    onItemClick(meeting)
                },
                onLongClick = {
                    onItemLongClick(meeting)
                },
            ),
        shape = AppShapes.small,
        colors = lightMeetingCardColours
    ) {
        // Initial display of Meeting details.
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxWidth()
        ) {
            meeting.sellCode?.let { code ->
                Text(
                    code,
                    Modifier.layoutId("idSellCode")
                )
            }
            var name = meeting.meetingName
            if (name.length > Constants.MEETING_NAME_MAX) {
                name = "${name.take(Constants.MEETING_NAME_TAKE)} ..."
            }
            Text(
                name,
                Modifier.layoutId("idVenueName")
            )
            Text(
                "(${meeting.venueMnemonic})",
                Modifier.layoutId("idVenueMnemonic"),
                fontSize = fontSize12sp
            )
            Text(
                "${meeting.meetingTime}",
                Modifier.layoutId("idTime"),
                fontSize = fontSize12sp
            )
            IconButton(
                onClick = {
                    expandedState = !expandedState
                },
                Modifier
                    .layoutId("idArrow")
                    .rotate(rotationState)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow"
                )
            }
        }
        if (expandedState) {
            // Meeting extra info, the 'expanded' state.
            MeetingItemExtra(meeting, onItemClick)
        }
    }
}

private val constraintSet = ConstraintSet {
    val idSellCode = createRefFor("idSellCode")
    val idVenueName = createRefFor("idVenueName")
    val idVenueMnemonic = createRefFor("idVenueMnemonic")
    val idTime = createRefFor("idTime")
    val idArrow = createRefFor("idArrow")

    constrain(idSellCode) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin16dp)
    }
    constrain(idVenueName) {
        start.linkTo(idSellCode.end, margin = margin16dp)
        top.linkTo(idSellCode.top, margin = margin0dp)
    }
    constrain(idVenueMnemonic) {
        start.linkTo(idVenueName.end, margin = margin8dp)
        top.linkTo(idVenueName.top, margin = margin0dp)
    }
    constrain(idTime) {
        top.linkTo(idVenueName.top, margin = margin0dp)
        end.linkTo(idArrow.start, margin = margin8dp)
    }
    constrain(idArrow) {
        end.linkTo(parent.absoluteRight)
        centerVerticallyTo(parent)
    }
}
