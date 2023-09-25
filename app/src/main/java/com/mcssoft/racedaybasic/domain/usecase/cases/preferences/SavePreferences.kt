package com.mcssoft.racedaybasic.domain.usecase.cases.preferences

import com.mcssoft.racedaybasic.data.repository.preferences.IPreferences
import com.mcssoft.racedaybasic.data.repository.preferences.Preference
import com.mcssoft.racedaybasic.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavePreferences @Inject constructor(
    private val preferences: IPreferences
) {
    /**
     * Save the FromDb preference to the datastore.
     * @param value: The value to save.
     */
    operator fun invoke(pref: Preference, value: Boolean): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            preferences.setPreference(pref, value)

            emit(DataResult.success(value))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    operator fun invoke(pref: Preference, value: Long): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            preferences.setPreference(pref, value)

            emit(DataResult.success(value))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}