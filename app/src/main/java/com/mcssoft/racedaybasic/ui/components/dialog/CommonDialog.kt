package com.mcssoft.racedaybasic.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mcssoft.racedaybasic.ui.theme.*

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
    // TODO - This is unlikely to conform to Material specs.

    Dialog(
        onDismissRequest = onDismissClicked,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShapes.medium
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
                    //horizontalArrangement = Arrangement.Start
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
                                style = MaterialTheme.typography.h6
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
                                style = MaterialTheme.typography.h6
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
                    OutlinedButton(onClick = onDismissClicked)
                    {
                        Text(text = dismissButtonText)
                    }
                    if (onConfirmClicked != null) {
                        Spacer(modifier = Modifier.width(width16dp))
                        OutlinedButton(onClick = onConfirmClicked)
                        {
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