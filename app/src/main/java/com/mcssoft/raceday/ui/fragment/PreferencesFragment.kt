package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat
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

        // Initialise.
        spFileUse = findPreference("key_file_use")!!

        if(spFileUse.isChecked) {
            raceDayPreferences.setFileUse(true)
        } else {
            raceDayPreferences.setFileUse(false)
        }
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

    private lateinit var spFileUse: SwitchPreferenceCompat
}