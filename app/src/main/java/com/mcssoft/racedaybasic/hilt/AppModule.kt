package com.mcssoft.racedaybasic.hilt

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.data.datasource.database.RaceDayDb
import com.mcssoft.racedaybasic.data.datasource.remote.IRaceDay
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.preferences.IPreferences
import com.mcssoft.racedaybasic.data.repository.preferences.PreferencesImpl
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaybasic.data.repository.remote.RemoteRepoImpl
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaybasic.domain.usecase.cases.api.SetupBaseFromApi
import com.mcssoft.racedaybasic.domain.usecase.cases.api.SetupRunnersFromApi
import com.mcssoft.racedaybasic.domain.usecase.cases.local.SetupBaseFromLocal
import com.mcssoft.racedaybasic.domain.usecase.cases.meetings.GetMeeting
import com.mcssoft.racedaybasic.domain.usecase.cases.meetings.GetMeetings
import com.mcssoft.racedaybasic.domain.usecase.cases.preferences.GetPreferences
import com.mcssoft.racedaybasic.domain.usecase.cases.races.GetRaces
import com.mcssoft.racedaybasic.domain.usecase.cases.preferences.SavePreferences
import com.mcssoft.racedaybasic.domain.usecase.cases.races.GetRace
import com.mcssoft.racedaybasic.domain.usecase.cases.runners.GetRunners
import com.mcssoft.racedaybasic.domain.usecase.cases.runners.SetRunnerChecked
import com.mcssoft.racedaybasic.domain.usecase.cases.summary.GetSummaries
import com.mcssoft.racedaybasic.domain.usecase.cases.summary.SetForSummary
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(app: Application): RaceDayDb {
        return Room.databaseBuilder(
            app,
            RaceDayDb::class.java,
            app.resources.getString(R.string.app_db_name)
        ).build()
    }

    @Provides
    fun provideRaceDayDao(db: RaceDayDb): IDbRepo {
        return db.getRaceDayDao()
    }

    @Provides
    fun provideApi(app: Application): IRaceDay {
//        val nwInterceptor = NetworkInterceptor()
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
//            .addInterceptor(nwInterceptor)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(app.resources.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
            .create(IRaceDay::class.java)
    }

    @Provides
    fun provideRepository(api: IRaceDay): IRemoteRepo {
        return RemoteRepoImpl(api)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatastore(@ApplicationContext context: Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile("settings") }
        )
    }

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): IPreferences {
        return PreferencesImpl(context)
    }

    @Provides
    fun provideUseCases(
        remote: IRemoteRepo,
        local: IDbRepo,
        prefs: IPreferences
    ): RaceDayUseCases {
        return RaceDayUseCases(
            setupBaseFromApi = SetupBaseFromApi(remote, local),
            setupBaseFromLocal = SetupBaseFromLocal(local),
            setupRunnersFromApi = SetupRunnersFromApi(),//context),
            getMeeting = GetMeeting(local),
            getMeetings = GetMeetings(local),
            getRaces = GetRaces(local),
            getRace = GetRace(local),
            getRunners = GetRunners(local),
            setRunnerChecked = SetRunnerChecked(local),
            getSummaries = GetSummaries(local),
            setForSummary = SetForSummary(local),
            getPreferences = GetPreferences(prefs),
            savePreferences = SavePreferences(prefs)
//            checkPrePopulate = CheckPrePopulate(local),
//            prePopulate = PrePopulate(local, context)
        )
    }

}
