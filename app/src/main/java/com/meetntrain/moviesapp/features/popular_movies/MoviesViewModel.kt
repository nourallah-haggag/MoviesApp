package com.meetntrain.moviesapp.features.popular_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.repo.MoviesRepo
import com.meetntrain.moviesapp.common.utils.launchViewModelCoroutineWithLoading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MoviesViewModel(private val repo: MoviesRepo) : ViewModel() {
    //TODO: handle errors
    // TODO : implement different states of the view model (sealed class + channel that will broadcast the latest state the view model has reached
    //TODO: there will only be one live data representing the state
    //TODO: we will decide what to do with a when statement

    val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()


    @ExperimentalCoroutinesApi
    fun getAllMovies() {
        viewModelScope.launchViewModelCoroutineWithLoading(apiCall = suspend { repo.getMovies() },
            data = moviesLiveData,
            isLoading = loadingLiveData)
    }


}