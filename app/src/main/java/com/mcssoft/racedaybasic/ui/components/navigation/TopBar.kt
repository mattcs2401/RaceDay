package com.mcssoft.racedaybasic.ui.components.navigation

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
 * @param onBackPressed: A handler to simulate on back pressed.
 * @param backNavIcon: Associated on back pressed icon.
 * @param actions: Other actions associated with the top bar.
 * @note onBackPressed and backNavIcon need to be implemented together.
 */
@Composable
fun TopBar(
    title: String,
    backgroundColour: Color,
    onBackPressed: () -> Unit = {},
    backNavIcon: Int? = null,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = backgroundColour,
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
        actions = actions
    )
}