package com.mcssoft.raceday.ui.components.preferences

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    backNavigate(navController, state)
                },
                actions = {}
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

fun backNavigate(
    navController: NavController,
    state: PreferencesState
) {
    navController.navigate(
        Screens.MeetingsScreen.route + "fromApi=${state.sourceFromApi}"
    ) {
        popUpTo(route = Screens.MeetingsScreen.route) {
            inclusive = true
        }
    }
}
