package com.mcssoft.racedaybasic.ui.components.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.mcssoft.racedaybasic.R

@Composable
fun BottomBar(
    navController: NavController
) {
    val bottomNavItems = listOf(
        BottomNavItem.Admin,
        BottomNavItem.Settings,
        BottomNavItem.Summary
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colourPrimary),
        contentColor = Color.White
    ) {
        bottomNavItems.forEach { item ->
            val currentRoute = item.route
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.5f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}
