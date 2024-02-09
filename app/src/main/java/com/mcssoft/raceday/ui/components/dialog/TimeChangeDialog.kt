package com.mcssoft.raceday.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.theme.AppShapes
import com.mcssoft.raceday.ui.theme.height16dp
import com.mcssoft.raceday.ui.theme.padding16dp
import com.mcssoft.raceday.ui.theme.padding8dp
import com.mcssoft.raceday.ui.theme.width16dp

@Composable
@ExperimentalMaterial3Api
fun TimeChangeDialog(
    timeState: TimePickerState,
    showDialog: Boolean,
    onDismissClicked: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    if (showDialog) {
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = padding16dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = LocalContext.current.resources
                                .getString(R.string.dlg_change_start_time),
                            modifier = Modifier.padding(
                                top = padding8dp,
                                bottom = padding8dp
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .height(height16dp)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = padding16dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TimePicker(state = timeState)
                    }
                    Spacer(
                        modifier = Modifier
                            .height(height16dp)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = padding16dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = onDismissClicked
                        ) {
                            Text(text = "Dismiss")
                        }
                        Spacer(modifier = Modifier.width(width16dp))
                        OutlinedButton(
                            onClick = onConfirmClicked
                        ) {
                            Text(text = "Confirm")
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
}

