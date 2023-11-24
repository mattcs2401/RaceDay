package com.mcssoft.raceday.domain.usecase.preferences

import com.mcssoft.raceday.data.repository.preferences.app.IAppPreferences
import com.mcssoft.raceday.data.repository.preferences.app.AppPreference
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPreferences @Inject constructor(
    private val preferences: IAppPreferences
) {
    operator fun invoke(pref: AppPreference): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            val result = preferences.getPreference(pref)

            emit(DataResult.success(result))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}