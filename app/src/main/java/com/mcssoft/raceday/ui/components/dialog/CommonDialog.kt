package com.mcssoft.raceday.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mcssoft.raceday.ui.theme.AppShapes
import com.mcssoft.raceday.ui.theme.eightyPercent
import com.mcssoft.raceday.ui.theme.height16dp
import com.mcssoft.raceday.ui.theme.padding16dp
import com.mcssoft.raceday.ui.theme.twentyPercent
import com.mcssoft.raceday.ui.theme.width16dp

/**
 * A generic dialog with 8dp rounded corners..
 * @param icon: Optional dialog icon id (type R.drawable) positioned to the left of the title. A
 *              48dp icon seems to work best.
 * @param dialogTitle: The dialog title (defaults to Bold and Typography.H6).
 * @param dialogText: Dialog text/message.
 * @param dismissButtonText: The dismiss button text/label.
 * @param onDismissClicked: Dismiss button onClick handler.
 * @param confirmButtonText: Optional Confirm button text/label.
 * @param onConfirmClicked: Optional Confirm button onClick handler.
 * @param backgroundColour: Colour of the dialog's background (default Color.White).
 */
@Composable
fun CommonDialog(
    icon: Int? = null,
    dialogTitle: String,
    dialogText: String,
    dismissButtonText: String,
    onDismissClicked: () -> Unit,
    confirmButtonText: String = "",
    onConfirmClicked: (() -> Unit)? = null,
    backgroundColour: Color = Color.White
) {
    Dialog(
        onDismissRequest = onDismissClicked,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = AppShapes.medium
        ) {
            // A single columns with 3 Rows and Spacers in between.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColour)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(height16dp)
                        .fillMaxWidth()
                )
                // 1st Row, icon and dialog title.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = padding16dp),
                    verticalAlignment = Alignment.CenterVertically
                    // horizontalArrangement = Arrangement.Start
                ) {
                    if (icon != null) {
                        Column(
                            modifier = Modifier.fillMaxWidth(twentyPercent),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Image(
                                painter = painterResource(icon),
                                contentDescription = "dialog icon"
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(eightyPercent),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = dialogTitle,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dialogTitle,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }

                // 2nd Row, dialog text.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding16dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = dialogText,
                        textAlign = TextAlign.Center
                    )
                }

                // 3rd Row, Dismiss button (and optional Confirm button).
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = padding16dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismissClicked) {
                        Text(text = dismissButtonText)
                    }
                    if (onConfirmClicked != null) {
                        Spacer(modifier = Modifier.width(width16dp))
                        OutlinedButton(onClick = onConfirmClicked) {
                            Text(text = confirmButtonText)
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(height16dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

// @Preview
// @Composable
// fun ShowCommonDialog() {
//    CommonDialog(
//        icon = R.drawable.ic_error_48,
//        dialogTitle = "An errorDto occurred",
//        dialogText = "Some common dialog text.",
//        dismissButtonText = "Ok",
//        onDismissClicked = {}
//    )
// }
