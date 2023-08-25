package com.mcssoft.racedaybasic.hilt

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.data.datasource.database.RaceDayDb
import com.mcssoft.racedaybasic.data.datasource.remote.IRaceDay
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaybasic.data.repository.remote.RemoteRepoImpl
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaybasic.domain.usecase.cases.api.SetupBaseFromApi
import com.mcssoft.racedaybasic.domain.usecase.cases.meetings.GetMeeting
import com.mcssoft.racedaybasic.domain.usecase.cases.meetings.GetMeetings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
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

//    @Provides
//    @Singleton
//    fun providePreferences(@ApplicationContext context: Context): IPreferences {
//        return PreferencesImpl(context)
//    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideUseCases(
        remote: IRemoteRepo,
        local: IDbRepo,
//        prefs: IPreferences,
        context: Context,
    ): RaceDayUseCases {
        return RaceDayUseCases(
            setupBaseFromApi = SetupBaseFromApi(remote, local),
//            setupRunnerFromApi = SetupRunnerFromApi(),//context),
//            getMeeting = GetMeeting(local),
            getMeetings = GetMeetings(local),
//            getRaces = GetRaces(local),
//            getRace = GetRace(local),
//            getRunners = GetRunners(local),
//            getPreferences = GetPreferences(prefs),
//            savePreferences = SavePreferences(prefs),
//            setRunnerChecked = SetRunnerChecked(local),
//            getSummaries = GetSummaries(local),
//            setForSummary = SetForSummary(local),
//            checkPrePopulate = CheckPrePopulate(local),
//            prePopulate = PrePopulate(local, context)
        )
    }

}