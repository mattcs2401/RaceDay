package com.mcssoft.raceday.utility.callback

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

class BackPressCB(private val context: Context, enabled: Boolean) : OnBackPressedCallback(enabled) {

    override fun handleOnBackPressed() {
//        Toast.makeText(context, "Back navigation disabled from here.", Toast.LENGTH_SHORT).show()
        // do nothing
//        Log.d("tag","BackPressCB.handleOnBackPressed")
    }

    fun removeCallback() {
        super.remove()
//        Log.d("tag","BackPressCB.removeCallback")
    }
}