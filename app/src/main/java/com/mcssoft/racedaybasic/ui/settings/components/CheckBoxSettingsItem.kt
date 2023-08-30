package com.mcssoft.racedaybasic.ui.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mcssoft.racedaybasic.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaybasic.ui.theme.eightyPercent
import com.mcssoft.racedaybasic.ui.theme.padding8dp
import com.mcssoft.racedaybasic.ui.theme.stroke2dp
import com.mcssoft.racedaybasic.ui.theme.twentyPercent

@Composable
fun CheckBoxSettingsItem(
    textTitle: String,
    textDescription: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,  // Note: nullable really only for @Preview.
    backgroundColour: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShapes.small,
        backgroundColor = backgroundColour,
        border = BorderStroke(
            width = stroke2dp,
            color = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(eightyPercent)
                .padding(padding8dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = textTitle,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = textDescription
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(twentyPercent)
                .padding(padding8dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                    checkmarkColor = MaterialTheme.colors.onBackground
                )
            )
        }

    }
}

@Preview
@Composable
fun showCBSI() {
    CheckBoxSettingsItem(
        textTitle = "Testing",
        textDescription = "Some testing",
        checked = true,
        onCheckedChange = {},
        backgroundColour = MaterialTheme.colors.primary
    )
}