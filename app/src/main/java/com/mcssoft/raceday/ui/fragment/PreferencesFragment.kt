package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.preference.*
import com.mcssoft.raceday.R
import com.mcssoft.raceday.repository.RaceDayPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat(),
        Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    @Inject lateinit var raceDayPreferences: RaceDayPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Only because context is used numerous times.
        val context = requireContext()

        // The main preference screen (all preference widgets are a child of / added to, this).
        screen = preferenceManager.createPreferenceScreen(context)

        setUseFile()

        setDefaultRaceCode()

        preferenceScreen = screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set toolbar title.
        requireActivity().findViewById<Toolbar>(R.id.id_toolbar)?.title = "Preferences"

        initialise()

    }

    //<editor-fold default state="collapsed" desc="Region: Listeners">
    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        when(preference.key) {
            "key_file_use" -> {
                raceDayPreferences.setFileUse(newValue as Boolean)
            }
            "key_default_code_R" -> {
                raceDayPreferences.setDefaultCodeR(newValue as Boolean)
            }
            "key_default_code_T" -> {
                raceDayPreferences.setDefaultCodeT(newValue as Boolean)
            }
            "key_default_code_G" -> {
                raceDayPreferences.setDefaultCodeG(newValue as Boolean)
            }
        }
        return true
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        /** Note: OnPreferenceChange will have already happened. **/

        raceDayPreferences.setDefaultRaceCodes()

//        when (preference.key) {
//            "bp" -> "bp"
//        }

        return true
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Utility">
    private fun initialise() {
        if(spFileUse.isChecked)
            raceDayPreferences.setFileUse(true)
        else raceDayPreferences.setFileUse(false)

        if(cbpDefaultCodeR.isChecked)
            raceDayPreferences.setDefaultCodeR(true)
        else raceDayPreferences.setDefaultCodeR(false)

        if(cbpDefaultCodeT.isChecked)
            raceDayPreferences.setDefaultCodeT(true)

        if(cbpDefaultCodeG.isChecked)
            raceDayPreferences.setDefaultCodeG(true)
        else raceDayPreferences.setDefaultCodeG(false)

        raceDayPreferences.setDefaultRaceCodes()
    }

    // Switch preference as whether to re-use existing download file data.
    private fun setUseFile() {
        spFileUse = SwitchPreferenceCompat(context).apply {
            key="key_file_use"
            title="Use saved file."
            setDefaultValue(true)
            summary="Reload application data using saved file."
        }
        screen.addPreference(spFileUse)
    }

    private fun setDefaultRaceCode() {
        // Preference category for default Race code, e.g. R. T, G (code S to be advised).
        pcDefaultRaceCode = PreferenceCategory(context).apply {
            title = "Default Race Code"
            summary = "Select the default Race code for the list of displayed meetings."
        }
        screen.addPreference(pcDefaultRaceCode)

        cbpDefaultCodeR = CheckBoxPreference(context).apply {
            key="key_default_code_R"
            title="R"
            setDefaultValue(true)
        }
        pcDefaultRaceCode.addPreference(cbpDefaultCodeR)

        cbpDefaultCodeT = CheckBoxPreference(context).apply {
            key="key_default_code_T"
            title="T"
            setDefaultValue(false)
        }
        pcDefaultRaceCode.addPreference(cbpDefaultCodeT)

        cbpDefaultCodeG = CheckBoxPreference(context).apply {
            key="key_default_code_G"
            title="G"
            setDefaultValue(false)
        }
        pcDefaultRaceCode.addPreference(cbpDefaultCodeG)
    }
    //</editor-fold>

    private lateinit var screen: PreferenceScreen

    private lateinit var spFileUse: SwitchPreferenceCompat

    private lateinit var pcDefaultRaceCode: PreferenceCategory
    private lateinit var cbpDefaultCodeR: CheckBoxPreference
    private lateinit var cbpDefaultCodeT: CheckBoxPreference
    private lateinit var cbpDefaultCodeG: CheckBoxPreference
}