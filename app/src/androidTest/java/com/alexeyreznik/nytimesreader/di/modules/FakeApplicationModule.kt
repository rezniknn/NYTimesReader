package com.alexeyreznik.nytimesreader.di.modules

import com.alexeyreznik.nytimesreader.data.repositories.StoriesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by alexeyreznik on 19/01/2018.
 */
@Module
class FakeApplicationModule(private val storiesRepository: StoriesRepository) {

    @Provides
    @Singleton
    fun provideStoriesRepository(): StoriesRepository {
        return storiesRepository
    }
}