package com.mcssoft.raceday.ui.components.preferences.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.raceday.ui.components.preferences.PreferencesEvent
import com.mcssoft.raceday.ui.theme.AppShapes
import com.mcssoft.raceday.ui.theme.fontSize12sp
import com.mcssoft.raceday.ui.theme.fontSize16sp
import com.mcssoft.raceday.ui.theme.margin0dp
import com.mcssoft.raceday.ui.theme.margin16dp
import com.mcssoft.raceday.ui.theme.padding4dp

@Composable
fun PreferencesItem(
    settingsState: Boolean,
    title: String,
    description: String,
    enabled: Boolean,
    onEvent: (PreferencesEvent) -> Unit,
    eventType: PreferencesEvent.EventType
) {
    val textStyle = TextStyle(textDecoration = TextDecoration.None)

    var isChecked by remember { mutableStateOf(false) }
    isChecked = settingsState

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = AppShapes.medium,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxWidth(1f)
        ) {
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
                    onEvent(
                        PreferencesEvent.Checked(state, eventType)
                    )
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
