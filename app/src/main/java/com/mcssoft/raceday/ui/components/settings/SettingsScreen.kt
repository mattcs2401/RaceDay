package com.mcssoft.raceday.ui.components.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.navigation.Screen
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.settings.components.SettingsItem
import com.mcssoft.raceday.ui.theme.width8dp
import com.mcssoft.raceday.ui.components.settings.SettingsEvent.EventType

@Composable
fun SettingsScreen(
    state: SettingsState,
    navController: NavController,
    onEvent: (SettingsEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_settings),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    // As yet, haven't been able to make the meetingId param optional.
                    navController.navigate(Screen.MeetingsScreen.route) {
                        popUpTo(route = Screen.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // As yet, haven't been able to make the meetingId param optional.
                        navController.navigate(Screen.MeetingsScreen.route) {
                            popUpTo(route = Screen.MeetingsScreen.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_home_24),
                            stringResource(id = R.string.lbl_icon_home)
                        )
                    }
                }
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column {
                SettingsItem(
                    settingsState = state,
                    title = stringResource(id = R.string.id_source_from_api) ,
                    description = stringResource(id = R.string.id_source_from_api_desc),
                    enabled = true,
                    onEvent = onEvent,
                    eventType = EventType.SOURCEFROMAPI
                )
                Spacer(modifier = Modifier.width(width8dp))
                SettingsItem(
                    settingsState = state,
                    title = stringResource(id = R.string.id_auto_add_trainers) ,
                    description = stringResource(id = R.string.id_auto_add_trainers_desc),
                    enabled = true,
                    onEvent = onEvent,
                    eventType = EventType.AUTOADDTRAINER
                )
            }

        }
    }
}
