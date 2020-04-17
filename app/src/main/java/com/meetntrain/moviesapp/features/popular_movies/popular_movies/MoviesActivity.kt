package com.meetntrain.moviesapp.features.popular_movies.popular_movies

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.utils.toggleLoadingState
import com.meetntrain.moviesapp.common.view_model.State
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MoviesActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModel()

    // recycler view
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesViewModel.channelLiveData.observe(this, Observer { state ->
            when (state) {
                is State.LoadingState -> toggleLoadingState(
                    isLoading = state.isLoading,
                    loadingView = progress_bar
                )
                is State.PresentingState<*> -> {
                    val data = state.data as List<Movie>
                    data.apply {
                        setupMoviesRecyclerView(this)
                    }
                    Toast.makeText(this, data[0].overview, Toast.LENGTH_SHORT).show()
                    Log.d("hi", data[0].overview)
                }
            }
        })
        moviesViewModel.getAllMovies()

    }

    // recycler view setup
    private fun setupMoviesRecyclerView(moviesList: List<Movie>) {
        moviesAdapter = MoviesAdapter(moviesList)
        rv_movies.adapter = moviesAdapter
    }
}
