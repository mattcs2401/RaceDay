package com.mcssoft.racedaybasic.ui.components.dialog

import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.racedaybasic.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaybasic.ui.theme.fontSize12sp

/**
A dialog with a title on the 1st line, and a circular progress indicator and message text on the
2nd line.
@param titleText: The dialog "Title" on the 1st line. e.g. "Loading".
@param msgText: A message to display on the right of a circular progress indicator (2nd line).
 */
@Composable
fun LoadingDialog(
    titleText: String,
    msgText: String,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
        )
    ) {
        Card(
            shape = RoundedCornerShapes.small
        ) {
            ConstraintLayout(
                constraintSet
            ) {
                Text(
                    text = titleText,
                    Modifier.layoutId("idDialogTitle")
                )
                CircularProgressIndicator(
                    Modifier.layoutId("idDialogIndicator")
                )
                Text(
                    text = msgText,
                    Modifier.layoutId("idDialogText"),
                    fontSize = fontSize12sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

private val constraintSet = ConstraintSet {
    val idDialogTitle = createRefFor("idDialogTitle")
    val idDialogIndicator = createRefFor("idDialogIndicator")
    val idDialogText = createRefFor("idDialogText")

    constrain(idDialogTitle) {
        top.linkTo(parent.top, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
    }
    constrain(idDialogIndicator) {
        top.linkTo(idDialogTitle.bottom, margin = 16.dp)
        start.linkTo(idDialogTitle.start, margin = 0.dp)
        bottom.linkTo(parent.bottom, margin = 16.dp)            // padding match top.
    }
    constrain(idDialogText) {
        top.linkTo(idDialogIndicator.top, margin = 8.dp)       // bring down a bit.
        start.linkTo(idDialogIndicator.end, margin = 16.dp)
        end.linkTo(parent.end, margin = 16.dp)                  // padding match start.
//        bottom.linkTo(parent.bottom, margin = 16.dp)            // padding match top.
    }
}

@Preview
@Composable
fun ShowDialog() {
    LoadingDialog(titleText = "Initialising", msgText = "Setup base from API.", {})
}


