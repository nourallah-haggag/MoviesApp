package com.meetntrain.moviesapp.common.utils

import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * extension function that launches a coroutine that would be responsible for loading data
 * @param apiCall a suspend function called from the injected repository that would return our data
 * @param data the live data object that would be updated in the view model and observed in the view
 * @param isLoading a boolean indicating the loading state
 */
fun <T> CoroutineScope.launchViewModelCoroutineWithLoading(
    apiCall: suspend () -> List<T>,
    data: MutableLiveData<List<T>>,
    isLoading: MutableLiveData<Boolean>
) {
    this.launch {
        isLoading.postValue(true)
        val responseData = apiCall.invoke()
        data.postValue(responseData)
        isLoading.postValue(false)
    }
}

/**
 * hide or show progress based on loading state
 * @param isLoading indicate current loading state (true if loading)
 * @param loadingView the that indicates loading that should be shown or hidden
 */
fun toggleLoadingState(isLoading:Boolean , loadingView:View)
{
    if(isLoading)
        loadingView.visibility=View.VISIBLE
    else
        loadingView.visibility=View.GONE
}