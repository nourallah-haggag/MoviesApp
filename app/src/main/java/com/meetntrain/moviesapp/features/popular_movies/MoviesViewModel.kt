package com.meetntrain.moviesapp.features.popular_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.repo.MoviesRepo
import com.meetntrain.moviesapp.common.utils.launchViewModelCoroutineWithLoading
import kotlinx.coroutines.launch

class MoviesViewModel(private val repo: MoviesRepo) : ViewModel() {
    //TODO: handle errors

    val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getAllMovies() {
        viewModelScope.launchViewModelCoroutineWithLoading(apiCall = suspend { repo.getMovies() },
            data = moviesLiveData,
            isLoading = loadingLiveData)
    }


}