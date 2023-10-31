package com.mcssoft.racedaybasic.ui.components.meetings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * Implement a custom TopAppBar component.
 * @param title: Title.
 * @param title2: Second title/entry.
 * @param backgroundColour: The background colour.
 * @param actions: Other actions associated with the top bar.
 * @note Special case of the TopAppBar, there is no back nav from the Meetings screen.
 */
@Composable
fun MeetingsTopBar(
    title: String,
    title2: String?,
    backgroundColour: Color,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = {
            Row(content = {
                Text(title, modifier = Modifier.weight(weight = 2f))
                if (title2 != null) {
                    Text(title2, fontSize = 12.sp, modifier = Modifier.weight(weight = 3f))
                }
            })
        },
        backgroundColor = backgroundColour,
        actions = actions
    )
}

@Preview
@Composable
fun ShowTopBar(title: String = "Meetings", title2: String = "2023-08-28") {
    MeetingsTopBar(title, title2, MaterialTheme.colors.primary)
}