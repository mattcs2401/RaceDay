package com.mcssoft.raceday.ui.components.navigation

sealed class PagerItems(
    val ordinal: Int,
    val title: String
) {
    data object Current: PagerItems(
        0,
        title = "Current"
    )

    data object Previous: PagerItems(
        1,
        title = "Previous"
    )
}