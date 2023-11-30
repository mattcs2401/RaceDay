package com.mcssoft.raceday.ui.components.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.ui.components.settings.SettingsEvent
import com.mcssoft.raceday.ui.components.settings.SettingsState
import com.mcssoft.raceday.ui.theme.RoundedCornerShapes
import com.mcssoft.raceday.ui.theme.elevation4dp
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.fontSize16sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.padding4dp

// Note: This is done all with Material3.

@Composable
fun SettingsItem(
    settingsState: SettingsState,
    title: String,
    description: String,
    enabled: Boolean,
    onEvent: (SettingsEvent) -> Unit
) {
    val textStyle = TextStyle(textDecoration = TextDecoration.None)

    var isChecked by remember { mutableStateOf(false) }
    isChecked = settingsState.sourceFromApi

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = RoundedCornerShapes.medium,
        elevation = elevation4dp,
        border = BorderStroke(2.dp, Color.Blue)
    ) {
        ConstraintLayout(
            constraintSet
        ){
            Text(
                text = title,
                Modifier.layoutId("idLabel"),
                fontSize = fontSize16sp,
                fontWeight = FontWeight.Bold,
                style = textStyle
            )
            Text(
                text = description,
                Modifier.layoutId("idDescription"),
                fontSize = fontSize12sp,
                style = textStyle
            )
            Switch(
                checked = isChecked,
                onCheckedChange = { state ->
                    isChecked = state
                    settingsState.sourceFromApi = state
                    onEvent(SettingsEvent.Checked(state))  // for datastore update.
                },
                Modifier.layoutId("idSwitch"),
                enabled = enabled
            )
        }
    }
}

private val constraintSet = ConstraintSet {
    val idLabel = createRefFor("idLabel")
    val idSwitch = createRefFor("idSwitch")
    val idDescription = createRefFor("idDescription")

    constrain(idLabel) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin16dp)
    }
    constrain(idSwitch) {
        top.linkTo(parent.top, margin = margin0dp)
        end.linkTo(parent.end, margin = margin16dp)
    }
    constrain(idDescription) {
        top.linkTo(idLabel.bottom, margin = margin16dp)
        start.linkTo(idLabel.start, margin = margin0dp)
        bottom.linkTo(parent.bottom, margin = margin16dp)
    }

}
