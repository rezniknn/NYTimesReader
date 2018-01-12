package com.alexeyreznik.nytimesreader.di.modules

import com.alexeyreznik.nytimesreader.data.repositories.StoriesRepository
import com.alexeyreznik.nytimesreader.domain.GetStoriesListUseCase
import com.alexeyreznik.nytimesreader.presentation.presenters.StoriesListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by alexeyreznik on 12/01/2018.
 */
@Module
class StoriesListModule {

    @Provides
    fun provideGetStoriesListUseCase(storiesRepository: StoriesRepository): GetStoriesListUseCase
            = GetStoriesListUseCase(storiesRepository)

    @Provides
    fun providePresenter(getStoriesListUseCase: GetStoriesListUseCase): StoriesListPresenter
            = StoriesListPresenter(getStoriesListUseCase)
}