package com.mcssoft.raceday.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun BottomBar(
    navController: NavController,
    displayLabels: Boolean
) {
// Reference:
// https://itnext.io/navigation-bar-bottom-app-bar-in-jetpack-compose-with-material-3-c57ae317bd00

    val bottomNavItems = listOf(
        BottomNavItems.Settings,
        BottomNavItems.Summary
    )

    val selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        ) {
            bottomNavItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = { navController.navigate(item.route) },
                    label = {
                        if(displayLabels) {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = "${item.title} Icon",
                        )
                    },
                    colors = NavigationBarItemDefaults.colors()
                    // TODO - bring NavigationBarItemColors into the Theme colours.
                )
            }
    }
}

//@Preview
//@Composable
//fun showBottomBar() {
//    BottomBar(null)
//}