package com.mcssoft.raceday.ui.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mcssoft.raceday.R

@Composable
fun ExceptionErrorDialog(
    dialogTitle: String,
    exceptionMsg: String, // message from any exception.
    dismissButtonText: String,
    onDismissClicked: () -> Unit
) {
    CommonDialog(
        icon = R.drawable.ic_error_48,
        dialogTitle = dialogTitle, // stringResource(id = R.string.dlg_error_title),
        dialogText = exceptionMsg, // .message!!, // stringResource(id = R.string.dlg_error_msg_unknown),
        dismissButtonText = dismissButtonText, //  stringResource(id = R.string.lbl_btn_cancel),
        onDismissClicked = onDismissClicked,
        confirmButtonText = stringResource(id = R.string.lbl_btn_refresh),
//            onConfirmClicked = {
//                show.value = !show.value
//                /** TODO - TBA what we do here ? **/
//            }
    )
}

// @Preview
// @Composable
// fun ShowErrorDialog() {
//    CommonDialog(
//        icon = R.drawable.ic_error_48,
//        dialogTitle = "Dialog Title",
//        dialogText = "An exception message here.",
//        dismissButtonText = "Ok",
//        onDismissClicked = { /*TODO*/ })
// }
