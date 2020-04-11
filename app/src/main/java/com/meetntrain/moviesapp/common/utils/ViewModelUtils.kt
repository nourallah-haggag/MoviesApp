package com.meetntrain.moviesapp.common.utils

import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * extension function that launches a coroutine that would be responsible for loading data
 * the coroutine runs on the main thread to collect the flow emissions and update the ui
 * the flow itself emits data on the io Thread
 * @param apiCall a suspend function called from the injected repository that would return our data
 * @param data the live data object that would be updated in the view model and observed in the view
 * @param isLoading a boolean indicating the loading state
 */
fun <T> CoroutineScope.launchViewModelCoroutineWithLoading(
    apiCall: suspend () -> Flow<T>,
    data: MutableLiveData<T>,
    isLoading: MutableLiveData<Boolean>
) {
    this.launch {
        isLoading.postValue(true)
        apiCall.invoke()
            .collect { responseData ->
                data.postValue(responseData)
            }
        isLoading.postValue(false)
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