package com.mcssoft.racedaybasic.ui.meetings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaybasic.ui.theme.fontSize12sp
import com.mcssoft.racedaybasic.ui.theme.margin0dp
import com.mcssoft.racedaybasic.ui.theme.margin16dp
import com.mcssoft.racedaybasic.ui.theme.margin8dp
import com.mcssoft.racedaybasic.ui.theme.padding4dp

@Composable
fun MeetingItem(
    meeting: Meeting,
    onItemClick: (Meeting) -> Unit,
) {
    val backgroundColour = /*if (meeting.abandoned) {
        MaterialTheme.colors.errorDto
    } else {*/
        MaterialTheme.colors.primaryVariant
//    }

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp)
            .animateContentSize(
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            ),
        shape = RoundedCornerShapes.small,
        backgroundColor = backgroundColour
    ) {
        // Initial display of Meeting details.
        ConstraintLayout(
            constraintSet,
            modifier = Modifier
                .clickable {
                    onItemClick(meeting)
                }
        ) {
            meeting.sellCode?.let { code ->
                Text(code,
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
//                if (!meeting.abandoned) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
//                }
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