package com.meetntrain.moviesapp.features.popular_movies.paginated_movies

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
class PaginatedMoviesViewModel(private val repo: MoviesRepo) : ViewModel() {
    //TODO: handle errors
    private val channel: ConflatedBroadcastChannel<State> = ConflatedBroadcastChannel()
    val channelLiveData = MutableLiveData<State>()

    init {
        consumeViewUpdates(channel)
    }

    fun getAllMovies(pageNo: Int) {
        viewModelScope.launchViewModelCoroutineWithLoading(
            apiCall = suspend { repo.getPaginatedMovies(pageNo = pageNo) },
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