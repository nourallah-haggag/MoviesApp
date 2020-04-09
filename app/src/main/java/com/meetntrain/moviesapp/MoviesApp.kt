package com.meetntrain.moviesapp

import android.app.Application
import com.meetntrain.moviesapp.common.activity.ActivityListener
import com.meetntrain.moviesapp.common.di.appModule
import com.meetntrain.moviesapp.common.di.networkModule
import com.meetntrain.moviesapp.common.di.repoModule
import com.meetntrain.moviesapp.common.di.viewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val currentActivityListener: ActivityListener by inject()
        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            modules(listOf(networkModule, appModule, repoModule, viewModelModule))
        }
        registerActivityLifecycleCallbacks(currentActivityListener)
    }
}