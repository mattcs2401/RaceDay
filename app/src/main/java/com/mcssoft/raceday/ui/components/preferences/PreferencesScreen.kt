package com.mcssoft.raceday.ui.components.preferences

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.navigation.TopBar
import com.mcssoft.raceday.ui.components.preferences.PreferencesEvent.EventType.SourceFromApi
import com.mcssoft.raceday.ui.components.preferences.components.PreferencesItem

@Composable
fun PreferencesScreen(
    state: PreferencesState,
    navController: NavController,
    onEvent: (PreferencesEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_settings),
                backgroundColour = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        // As yet, haven't been able to make the meetingId param optional.
                        navController.navigate(Screens.MeetingsScreen.route) {
                            popUpTo(route = Screens.MeetingsScreen.route) {
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
                PreferencesItem(
                    settingsState = state.sourceFromApi,
                    title = stringResource(id = R.string.id_source_from_api),
                    description = stringResource(id = R.string.id_source_from_api_desc),
                    enabled = true,
                    onEvent = onEvent,
                    eventType = SourceFromApi
                )
            }
        }
    }
}
