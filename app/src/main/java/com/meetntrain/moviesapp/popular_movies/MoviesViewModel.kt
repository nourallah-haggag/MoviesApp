package com.meetntrain.moviesapp.popular_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetntrain.moviesapp.common.repo.MoviesRepo
import com.meetntrain.moviesapp.common.utils.launchViewModelCoroutineWithLoading
import com.meetntrain.moviesapp.common.view_model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MoviesViewModel(private val repo: MoviesRepo) : ViewModel() {

    private val channel: ConflatedBroadcastChannel<State> = ConflatedBroadcastChannel()
    val channelLiveData = MutableLiveData<State>()

    fun getAllMovies() {
        consumeViewUpdates(channel)
        viewModelScope.launchViewModelCoroutineWithLoading(
            apiCall = suspend { repo.getMovies() },
            channel = channel
        )
    }

    private fun consumeViewUpdates(channel: ConflatedBroadcastChannel<State>) {
        viewModelScope.launch {
            channel.asFlow().collect {
                channelLiveData.postValue(it)
            }
        }

    }


}