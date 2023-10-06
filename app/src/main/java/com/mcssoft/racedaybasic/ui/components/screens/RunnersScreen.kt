package com.mcssoft.racedaybasic.ui.components.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mcssoft.racedaybasic.ui.components.runners.RunnersEvent
import com.mcssoft.racedaybasic.ui.components.runners.RunnersState

@Composable
/**
 * @param state: Races state.
 * @param navController: The Navigation.
 * @param onEvent: Call up to RacesEvent in ViewModel.
 */
fun RunnersScreen(
    state: RunnersState,
    navController: NavController,
    onEvent: (RunnersEvent) -> Unit   // TBA
) {

}