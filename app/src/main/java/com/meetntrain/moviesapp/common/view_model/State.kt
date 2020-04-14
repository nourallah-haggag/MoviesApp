package com.meetntrain.moviesapp.common.view_model

import androidx.lifecycle.MutableLiveData

sealed class State {
    data class LoadingState(val isLoading:Boolean) : State()
    data class PresentingState<T>(val data:T) : State()
    data class ErrorState(val error:Boolean) : State()

}
