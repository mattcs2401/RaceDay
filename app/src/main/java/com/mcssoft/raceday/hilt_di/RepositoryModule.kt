package com.mcssoft.raceday.hilt_di

import android.content.Context
import com.mcssoft.raceday.repository.ProtoRepository
import com.mcssoft.raceday.repository.RaceDayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProtoRepo(@ApplicationContext context: Context): ProtoRepository {
        return ProtoRepository(context)
    }

    @Singleton
    @Provides
    fun provideRaceDayRepo(@ApplicationContext context: Context): RaceDayRepository {
        return RaceDayRepository(context)
    }
}