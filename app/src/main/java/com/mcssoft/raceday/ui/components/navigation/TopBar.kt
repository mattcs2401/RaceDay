package com.mcssoft.raceday.ui.components.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

/**
 * Implement a custom TopAppBar component.
 * @param title: TopBar title.
 * @param backgroundColour: The background colour.
 * @param actions: Other actions associated with the top bar.
 * @note onBackPressed and backNavIcon need to be implemented together.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    colours: TopAppBarColors,
    backNavIcon: Int? = null,
    onBackPressed: () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = {
            Row(
                content = {
                    Text(
                        text = title
                    )
                }
            )
        },
        navigationIcon = {
            if (onBackPressed != {} && backNavIcon != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        painterResource(backNavIcon),
                        backNavIcon.toString()
                    )
                }
            }
        },
        colors = colours,
        actions = actions
    )
}

//@Preview
//@Composable
//fun ShowTopBar(title: String = "Meetings") {
//    TopBar(title, Color.White, MaterialTheme.colors.primary)
//}
