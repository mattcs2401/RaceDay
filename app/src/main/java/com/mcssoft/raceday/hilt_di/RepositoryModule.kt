package com.mcssoft.raceday.hilt_di

import android.content.Context
import com.mcssoft.raceday.repository.RaceDayRepository
import com.mcssoft.raceday.viewmodel.RaceDayViewModel
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
    fun provideRaceDayRepo(@ApplicationContext context: Context): RaceDayRepository {
        return RaceDayRepository(context)
    }

    @Provides
    fun provideMainViewModel(/*@ApplicationContext context: Context,*/ repository: RaceDayRepository): RaceDayViewModel {
        return RaceDayViewModel(/*context,*/ repository)
    }
}