package com.mcssoft.racedaybasic.ui.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.mcssoft.racedaybasic.R

@Composable
fun ErrorDialog(
//    show: MutableState<Boolean> = ,
    dialogTitle: String,
    message: String,  // message from any error TBA.
    dismissButtonText: String,
    onDismissClicked: () -> Unit
) {
//    if(show.value) {
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = dialogTitle,
            dialogText = message,
            dismissButtonText = dismissButtonText, //  stringResource(id = R.string.lbl_btn_cancel),
            onDismissClicked = onDismissClicked //{} //onDismissClicked,
//        confirmButtonText = stringResource(id = R.string.lbl_btn_refresh),
//            onConfirmClicked = {
//                show.value = !show.value
//                /** TODO - TBA what we do here ? **/
//            }
        )
//    }
}