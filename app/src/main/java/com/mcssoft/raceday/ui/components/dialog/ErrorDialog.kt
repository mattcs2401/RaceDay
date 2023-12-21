package com.mcssoft.raceday.ui.components.dialog

import androidx.compose.runtime.Composable
import com.mcssoft.raceday.R

@Composable
fun ErrorDialog(
    dialogTitle: String,
    message: String,  // message from any error TBA.
    dismissButtonText: String,
    onDismissClicked: () -> Unit
) {
    CommonDialog(
        icon = R.drawable.ic_error_48,
        dialogTitle = dialogTitle,
        dialogText = message,
        dismissButtonText = dismissButtonText,
        onDismissClicked = onDismissClicked
    )
}