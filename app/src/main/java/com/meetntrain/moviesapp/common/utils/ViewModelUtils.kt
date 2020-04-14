package com.meetntrain.moviesapp.common.utils

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.meetntrain.moviesapp.common.view_model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 * extension function that launches a coroutine that would be responsible for loading data
 * the coroutine runs on the main thread to collect the flow emissions and update the ui
 * the flow itself emits data on the io Thread
 * @param apiCall a suspend function called from the injected repository that would return our data
 * @param channel a conflated broadcast channel that will send/offer the value obtained from the api , this channel is the state provider for our view data , loading state and error states will be pushed on that channel
 */
@ExperimentalCoroutinesApi
fun <T> CoroutineScope.launchViewModelCoroutineWithLoading(
    apiCall: suspend () -> Flow<T>,
    channel: ConflatedBroadcastChannel<State>
) {
    this.launch {
        channel.offer(State.LoadingState(true))
        apiCall.invoke().onCompletion {
            channel.offer(State.LoadingState(false))
        }
            .collect { responseData ->
                channel.offer(State.PresentingState(responseData))
            }
    }
}

/**
 * hide or show progress based on loading state
 * @param isLoading indicate current loading state (true if loading)
 * @param loadingView the that indicates loading that should be shown or hidden
 */
fun toggleLoadingState(isLoading: Boolean, loadingView: View) {
    if (isLoading)
        loadingView.visibility = View.VISIBLE
    else
        loadingView.visibility = View.GONE
}