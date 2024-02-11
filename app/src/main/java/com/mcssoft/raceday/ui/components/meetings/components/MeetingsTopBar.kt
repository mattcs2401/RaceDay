package com.mcssoft.raceday.ui.components.meetings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/**
 * Implement a custom TopAppBar component.
 * @param title: Title.
 * @param title2: Second title/entry.
 * @param colours: The colours from a TopAppBarColors instance.
 * @param actions: Other actions associated with the top bar.
 * @note Special case of the TopAppBar, there is no back nav from the Meetings screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingsTopAppBar(
    title: String,
    title2: String?,
    colours: TopAppBarColors,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = {
            Row(content = {
                Text(
                    title,
                    modifier = Modifier.weight(weight = 2f),
                )
                if (title2 != null) {
                    Text(
                        title2,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(weight = 3f),
                    )
                }
            })
        },
        actions = actions,
        colors = colours
    )
}

//@Preview
//@Composable
//fun ShowTopBar(title: String = "Meetings", title2: String = "2023-08-28") {
//    MeetingsTopBar(title, title2, MaterialTheme.colorScheme.primary)
//}