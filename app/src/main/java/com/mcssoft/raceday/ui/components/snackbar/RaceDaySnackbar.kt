package com.mcssoft.raceday.ui.components.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Based on: https://www.youtube.com/watch?v=2FOShgaQk-I
@Composable
fun RaceDaySnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit?
) {
//    SnackbarHost(
//        hostState = snackbarHostState,
//        snackbar = { data ->
//            Snackbar(
//                modifier = Modifier.padding(16.dp),
//                content = {
//                    Text(
//                        text = data.message,
//                        style = MaterialTheme.typography.body2,
//                        color = Color.White
//                    )
//                },
//                action = {
//                    data.actionLabel?.let { actionLabel ->
//                        TextButton(
//                            onClick = {
//                                onDismiss()
//                            }
//                        ) {
//                            Text(
//                                text = actionLabel,
//                                style = MaterialTheme.typography.body2,
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            )
//        },
//        modifier = modifier
//    )
}
// Also: https://www.youtube.com/watch?v=hlfPlqTzfMk
