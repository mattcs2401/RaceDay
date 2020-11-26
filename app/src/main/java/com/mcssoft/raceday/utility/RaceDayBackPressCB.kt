package com.mcssoft.raceday.utility

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

/**
 * Call back to handle back press by the user.
 */
class RaceDayBackPressCB(enabled: Boolean) : OnBackPressedCallback(enabled) {

    override fun handleOnBackPressed() {
        // Do nothing, basically means back press is ignored.
    }

    fun removeCallback() {
        super.remove()
    }
}