package com.mcssoft.racedaybasic.ui.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaybasic.ui.components.navigation.Screen
import com.mcssoft.racedaybasic.ui.components.navigation.TopBar
import com.mcssoft.racedaybasic.ui.settings.components.CheckBoxSettingsItem
import com.mcssoft.racedaybasic.ui.theme.padding16dp
import com.mcssoft.racedaybasic.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val _checked: Boolean by viewModel.fromDbPref.collectAsStateWithLifecycle()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_preferences),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navController.navigate(Screen.MeetingsScreen.route) {
                        popUpTo(Screen.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
                actions = {}
            )
        },
        backgroundColor = Color.LightGray
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)// MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding16dp),
                verticalArrangement = Arrangement.Center
            ) {
                CheckBoxSettingsItem(
                    textTitle = stringResource(id = R.string.pref_load_from_db),
                    textDescription = "Load meeting details from local.",
                    checked = _checked,
                    onCheckedChange = { checked ->
                        Log.d("TAG","CheckBoxSettingsItem.onCheckChanged: checked=${checked}")
                        viewModel.saveFromDbPref(checked)
                    },
                    backgroundColour = MaterialTheme.colors.secondary
                )
//                Spacer(
//                    modifier = Modifier
//                        .height(height8dp)
//                        .fillMaxWidth()
//                )
//                CheckBoxSettingsItem(
//                    textTitle = stringResource(id = R.string.pref_only_au),
//                    textDescription = "Display only AU/NZ meeting details.",
//                    checked = onlyAuNzState.currValue,
//                    onCheckedChange = { checked ->
//                        viewModel.onEvent(SettingsEvent.SaveOnlyAuNzPref(checked))
//                    },
//                    backgroundColour = MaterialTheme.colors.secondary
//                )
            }

        }
    }

}


