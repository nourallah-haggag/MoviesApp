package com.meetntrain.moviesapp.common.repo

import android.util.Log
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.networking.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip

class MoviesRepo(private val moviesApi: MoviesApi) {
    @ExperimentalCoroutinesApi
    suspend fun getMovies(pageNo:Int): Flow<List<IMainScreenModel>> {
        return flowOf(moviesApi.getPaginatedMovies(pageNo).moviesList)
    }
}