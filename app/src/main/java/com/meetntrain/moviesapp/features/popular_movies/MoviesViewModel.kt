package com.meetntrain.moviesapp.features.popular_movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.repo.MoviesRepo
import com.meetntrain.moviesapp.common.utils.launchViewModelCoroutineWithLoading
import com.meetntrain.moviesapp.common.view_model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MoviesViewModel(private val repo: MoviesRepo) : ViewModel() {
    //TODO: handle errors
    val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val channel: ConflatedBroadcastChannel<State> = ConflatedBroadcastChannel()

    init {
        updateView(channel)
    }

    fun getAllMovies() {
        viewModelScope.launchViewModelCoroutineWithLoading(
            apiCall = suspend { repo.getMovies() },
            channel = channel
        )
    }

    private fun updateView(channel: ConflatedBroadcastChannel<State>) {
        viewModelScope.launch {
            channel.asFlow().collect {
                when (it) {
                    is State.LoadingState -> if (it.isLoading) {
                        Log.d("status", "loading")
                        loadingLiveData.postValue(true)
                    } else {
                        Log.d("status", "done")
                        loadingLiveData.postValue(false)
                    }
                    is State.PresentingState<*> -> {
                        val data = it.data as List<Movie>
                        Log.d("status", data[0].title)
                        moviesLiveData.postValue(data)
                    }
                }
            }
        }

    }


}