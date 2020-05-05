package com.meetntrain.moviesapp.common.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.meetntrain.moviesapp.common.view_model.State

open class BaseActivity : AppCompatActivity() {

    fun <T> observeMovie(
        liveData: LiveData<State>,
        success: (data: T) -> Unit,
        loading: (loadingState: Boolean) -> Unit,
        error: () -> Unit
    ) {
        liveData.observe(this, Observer { state ->
            when (state) {
                is State.LoadingState -> loading.invoke(state.isLoading)
                is State.PresentingState<*> -> {
                    val data = state.data as T
                    success.invoke(data)
                }
                is State.ErrorState -> error.invoke()
            }
        })
    }
}