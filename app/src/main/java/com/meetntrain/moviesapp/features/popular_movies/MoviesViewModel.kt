package com.meetntrain.moviesapp.features.popular_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.repo.MoviesRepo
import kotlinx.coroutines.launch

class MoviesViewModel(private val repo: MoviesRepo) : ViewModel() {
    val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()


    fun getAllMovies() {
        viewModelScope.launch {
            val movies = repo.getMovies()
            moviesLiveData.postValue(movies)
        }
    }


}