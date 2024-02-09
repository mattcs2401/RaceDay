package com.mcssoft.raceday.ui.components.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.theme.lightIconButtonColours
import com.mcssoft.raceday.ui.theme.width32dp

@Composable
fun BottomBar(
    navController: NavController?
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = Color.White,
        actions = {
            Spacer(modifier = Modifier.width(width32dp))
            IconButton(
                onClick = {
                    navController?.navigate(Screens.PreferencesScreen.route)
                 },
                colors = lightIconButtonColours
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_settings_24),
                    contentDescription = "Settings"
                )
            }
            Spacer(modifier = Modifier.width(width32dp))
            IconButton(
                onClick = {
                    navController?.navigate(Screens.SummaryScreen.route)
                },
                colors = lightIconButtonColours
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_summary_24),
                    contentDescription = "Summary"
                )
            }
        }
    )
}

@Preview
@Composable
fun showBottomBar() {
    BottomBar(null)
}