package com.mcssoft.raceday.ui.components.meetings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.margin8dp
import com.mcssoft.raceday.ui.theme.padding4dp
import com.mcssoft.raceday.utility.Constants.THREE_HUNDRED

@OptIn(ExperimentalFoundationApi::class) // for Long click.
@Composable
fun MeetingItem(
    meeting: Meeting,
    onItemClick: (Meeting) -> Unit,
    onItemLongClick: (Meeting) -> Unit
) {
    val backgroundColour = MaterialTheme.colors.primaryVariant

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f,
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp)
            .animateContentSize(
                animationSpec = tween(THREE_HUNDRED, easing = LinearOutSlowInEasing)
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
        shape = RoundedCornerShapes.small,
        backgroundColor = backgroundColour
    ) {
        // Initial display of Meeting details.
        ConstraintLayout(
            constraintSet
        ) {
            meeting.sellCode?.let { code ->
                Text(
                    code,
                    Modifier.layoutId("idSellCode")
                )
            }
            Text(
                meeting.meetingName,
                Modifier.layoutId("idVenueName")
            )
            Text(
                "(${meeting.venueMnemonic})",
                Modifier.layoutId("idVenueMnemonic"),
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
    val idMLoc = createRefFor("idSellCode")
    val idVenueName = createRefFor("idVenueName")
    val idVenueMnemonic = createRefFor("idVenueMnemonic")
    val idTime = createRefFor("idTime")
    val idArrow = createRefFor("idArrow")

    constrain(idMLoc) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin16dp)
    }
    constrain(idVenueName) {
        start.linkTo(idMLoc.end, margin = margin16dp)
        top.linkTo(idMLoc.top, margin = margin0dp)
    }
    constrain(idVenueMnemonic) {
        start.linkTo(idVenueName.end, margin = margin8dp)
        top.linkTo(idVenueName.top, margin = margin0dp)
    }
    constrain(idTime) {
        top.linkTo(idVenueName.top, margin = margin0dp)
        end.linkTo(idArrow.start, margin = margin16dp)
    }
    constrain(idArrow) {
        end.linkTo(parent.absoluteRight)
        centerVerticallyTo(parent)
    }
}
