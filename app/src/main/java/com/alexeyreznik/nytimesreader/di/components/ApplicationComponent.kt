package com.alexeyreznik.nytimesreader.di.components

import android.app.Application
import com.alexeyreznik.nytimesreader.di.modules.ApplicationModule
import com.alexeyreznik.nytimesreader.di.modules.StoriesListModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by alexeyreznik on 12/01/2018.
 */
@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun inject(application: Application)
    fun addStoriesModule(storiesListModule: StoriesListModule): StoriesListComponent
}