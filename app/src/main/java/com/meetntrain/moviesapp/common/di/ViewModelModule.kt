package com.meetntrain.moviesapp.common.di

import com.meetntrain.moviesapp.paginated_movies.PaginatedMoviesViewModel
import com.meetntrain.moviesapp.popular_movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MoviesViewModel(
            repo = get()
        )
    }

    viewModel {
        PaginatedMoviesViewModel(repo = get())
    }
}