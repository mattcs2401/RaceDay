package com.mcssoft.raceday.domain.usecase.preferences

import com.mcssoft.raceday.data.repository.preferences.app.IAppPreferences
import com.mcssoft.raceday.data.repository.preferences.app.AppPreference
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavePreferences @Inject constructor(
    private val preferences: IAppPreferences
) {
    /**
     * Save the FromDb preference to the datastore.
     * @param value: The value to save.
     */
    operator fun invoke(pref: AppPreference, value: Boolean): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            preferences.setPreference(pref, value)

            emit(DataResult.success(value))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    operator fun invoke(pref: AppPreference, value: Long): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            preferences.setPreference(pref, value)

            emit(DataResult.success(value))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}