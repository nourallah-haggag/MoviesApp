package com.meetntrain.moviesapp.common.repo

import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.networking.MoviesApi

class MoviesRepo(private val moviesApi: MoviesApi) {
    suspend fun getMovies(): List<Movie> {
        return moviesApi.getMoviesList().moviesList
    }
}