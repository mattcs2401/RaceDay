package com.mcssoft.raceday.ui.components.preferences

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.components.navigation.Screens
import com.mcssoft.raceday.ui.components.preferences.PreferencesEvent.EventType.SourceFromApi
import com.mcssoft.raceday.ui.components.preferences.components.PreferencesItem
import com.mcssoft.raceday.ui.theme.padding64dp
import com.mcssoft.raceday.ui.theme.components.card.topappbar.lightPreferencesTopAppBarColours

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreferencesScreen(
    state: PreferencesState,
    navController: NavController,
    onEvent: (PreferencesEvent) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(content = {
                        Text(
                            stringResource(id = R.string.label_settings),
                            modifier = Modifier.weight(weight = 2f),
                        )
                    })
                },
                colors = lightPreferencesTopAppBarColours,
                navigationIcon = {
                    IconButton(
                        onClick = { backNavigate(navController, state) }
                    ) {
                        Icon(
                            painterResource( id = R.drawable.ic_arrow_back_24 ),
                            stringResource(id = R.string.label_settings)
                        )
                    }
                },
                actions = {}
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    top = padding64dp
                )
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
