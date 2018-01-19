package com.alexeyreznik.nytimesreader.di.components

import com.alexeyreznik.nytimesreader.di.modules.FakeApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by alexeyreznik on 19/01/2018.
 */
@Singleton
@Component(modules = [(FakeApplicationModule::class)])
interface FakeApplicationComponent : ApplicationComponent