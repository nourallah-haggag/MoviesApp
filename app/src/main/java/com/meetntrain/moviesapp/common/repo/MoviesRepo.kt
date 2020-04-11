package com.meetntrain.moviesapp.common.repo

import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.networking.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class MoviesRepo(private val moviesApi: MoviesApi) {
    @ExperimentalCoroutinesApi
    suspend fun getMovies(): Flow<List<Movie>> {
        return flowOf(moviesApi.getMoviesList().moviesList).flowOn(Dispatchers.IO)
    }
}