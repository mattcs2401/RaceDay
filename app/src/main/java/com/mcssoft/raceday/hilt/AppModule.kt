package com.mcssoft.raceday.hilt

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.mcssoft.raceday.R
import com.mcssoft.raceday.data.datasource.database.RaceDayDb
import com.mcssoft.raceday.data.datasource.remote.IRaceDay
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.remote.IRemoteRepo
import com.mcssoft.raceday.data.repository.remote.RemoteRepoImpl
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.domain.usecase.api.SetupBaseFromApi
import com.mcssoft.raceday.domain.usecase.api.SetupRunnersFromApi
import com.mcssoft.raceday.utility.network.ConnectivityObserver
import com.mcssoft.raceday.utility.network.IConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): RaceDayDb {
        return Room.databaseBuilder(
            app,
            RaceDayDb::class.java,
            app.resources.getString(R.string.app_db_name)
        )
            .fallbackToDestructiveMigration() // TBA.
            .build()
    }

    @Singleton
    @Provides
    fun provideRaceDayDao(db: RaceDayDb): IDbRepo {
        return db.getRaceDayDao()
    }

    @Singleton
    @Provides
    fun provideApi(app: Application): IRaceDay {
        val client = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(app.resources.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IRaceDay::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: IRaceDay): IRemoteRepo {
        return RemoteRepoImpl(api)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager {
        return context.getSystemService(ConnectivityManager::class.java)
    }

    @Singleton
    @Provides
    fun provideConnectivityObserver(
        @ApplicationContext context: Context,
        connectivityManager: ConnectivityManager
    ): IConnectivityObserver {
        return ConnectivityObserver(context, connectivityManager)
    }

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    fun provideUseCases(
        remote: IRemoteRepo,
        local: IDbRepo,
        context: Context
    ): UseCases {
        return UseCases(
            setupBaseFromApi = SetupBaseFromApi(remote, local, context),
            setupRunnersFromApi = SetupRunnersFromApi(local, context)
        )
    }
}
