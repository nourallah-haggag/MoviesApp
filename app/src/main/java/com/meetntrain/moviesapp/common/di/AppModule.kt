package com.meetntrain.moviesapp.common.di

import com.meetntrain.moviesapp.common.activity.ActivityListener
import com.meetntrain.moviesapp.common.activity.ActivityRetriever
import org.koin.dsl.module

val appModule = module {
    single { ActivityListener() }
    // the view class will use the activity retriever class to get the current activity which will be needed by the viewmodelproviders class to get the main view model
    single { ActivityRetriever(get()) }
}