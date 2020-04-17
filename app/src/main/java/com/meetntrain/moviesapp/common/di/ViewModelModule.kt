package com.meetntrain.moviesapp.common.di

import com.meetntrain.moviesapp.features.popular_movies.popular_movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MoviesViewModel(
            get()
        )
    }
}