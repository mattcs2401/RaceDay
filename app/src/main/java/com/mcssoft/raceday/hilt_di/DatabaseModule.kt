package com.mcssoft.raceday.hilt_di

import android.content.Context
import androidx.room.Room
import com.mcssoft.raceday.database.RaceDay
import com.mcssoft.raceday.database.dao.IFileDataDAO
import com.mcssoft.raceday.database.dao.IRaceDayDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RaceDay {
        return Room.databaseBuilder(
            appContext,
            RaceDay::class.java,
            "race_day.db"
        ).build()
    }

    @Provides
    fun provideFileMetaDataDao(database: RaceDay): IFileDataDAO {
        return database.fileMetaDataDao()
    }

    @Provides
    fun provideRaceDayDetailsDao(database: RaceDay): IRaceDayDAO {
        return database.raceDayDetailsDao()
    }

}