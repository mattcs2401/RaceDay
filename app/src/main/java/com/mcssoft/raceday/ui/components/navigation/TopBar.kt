package com.mcssoft.raceday.ui.components.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

/**
 * Implement a custom TopAppBar component.
 * @param title: TopBar title.
 * @param backgroundColour: The background colour.
 * @param actions: Other actions associated with the top bar.
 * @note onBackPressed and backNavIcon need to be implemented together.
 */
@Composable
fun TopBar(
    title: String,
    titleColour: Color,
    backgroundColour: Color,
    onBackPressed: () -> Unit = {},
    backNavIcon: Int? = null,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = {
            Row(
                content = {
                    Text(
                        text = title,
                        color = titleColour
                    )
                }
            )
        },
        backgroundColor = backgroundColour,
        navigationIcon = {
            if (onBackPressed != {} && backNavIcon != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        painterResource(backNavIcon),
                        backNavIcon.toString(),
                        tint = titleColour
                    )
                }
            }
        },
        actions = actions
    )
}

//@Preview
//@Composable
//fun ShowTopBar(title: String = "Meetings") {
//    TopBar(title, Color.White, MaterialTheme.colors.primary)
//}
