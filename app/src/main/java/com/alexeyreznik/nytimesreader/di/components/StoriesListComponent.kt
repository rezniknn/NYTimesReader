package com.alexeyreznik.nytimesreader.di.components

import com.alexeyreznik.nytimesreader.di.modules.StoriesListModule
import com.alexeyreznik.nytimesreader.presentation.presenters.StoriesListPresenter
import dagger.Subcomponent

/**
 * Created by alexeyreznik on 12/01/2018.
 */
@Subcomponent(modules = [(StoriesListModule::class)])
interface StoriesListComponent {
    fun presenter(): StoriesListPresenter
}