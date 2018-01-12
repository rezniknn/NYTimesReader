package com.alexeyreznik.nytimesreader

import android.app.Application
import com.alexeyreznik.nytimesreader.di.components.ApplicationComponent
import com.alexeyreznik.nytimesreader.di.components.DaggerApplicationComponent
import com.alexeyreznik.nytimesreader.di.modules.ApplicationModule

/**
 * Created by alexeyreznik on 12/01/2018.
 */
class App : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
        component.inject(this)
    }

    private fun initAppComponent() {
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}