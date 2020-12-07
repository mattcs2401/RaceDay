package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mcssoft.raceday.R
import com.mcssoft.raceday.repository.RaceDayPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    @Inject lateinit var raceDayPreferences: RaceDayPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set toolbar title.
        requireActivity().findViewById<Toolbar>(R.id.id_toolbar)?.title = "Preferences"
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        when(preference.key) {
            "key_use_file" -> {
                raceDayPreferences.setFileUse(newValue as Boolean)
            }
        }
        return true
    }

//    override fun onPreferenceClick(preference: Preference): Boolean {
//        /** Note: OnPreferenceChange will have already happened. **/
//        when (preference.key) {
//            "bp" -> "bp"
//        }
//        return true
//    }
}