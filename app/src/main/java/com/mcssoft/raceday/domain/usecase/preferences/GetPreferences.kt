package com.mcssoft.raceday.domain.usecase.preferences

import com.mcssoft.raceday.data.repository.preferences.IPreferences
import com.mcssoft.raceday.data.repository.preferences.Preference
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPreferences @Inject constructor(
    private val preferences: IPreferences
) {
    operator fun invoke(pref: Preference): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            val result = preferences.getPreference(pref)

            emit(DataResult.success(result))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}