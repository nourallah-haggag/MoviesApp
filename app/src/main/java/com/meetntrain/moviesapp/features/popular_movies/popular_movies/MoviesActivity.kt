package com.meetntrain.moviesapp.features.popular_movies.popular_movies

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.utils.toggleLoadingState
import com.meetntrain.moviesapp.common.view_model.State
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.MoviesListAdapter
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.view_holder.ItemTouchListenerCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MoviesActivity : AppCompatActivity(), MoviesListAdapter.Interaction {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private lateinit var moviesList: MutableList<IMainScreenModel>

    // recycler view
    private lateinit var moviesAdapter: MoviesListAdapter

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
                    val data = state.data as List<IMainScreenModel>
                    data.apply {
                        setupMoviesRecyclerView(this)
                    }
                }
            }
        })
        moviesViewModel.getAllMovies()

    }

    // recycler view setup
    private fun setupMoviesRecyclerView(moviesList: List<IMainScreenModel>) {
        moviesAdapter =
            MoviesListAdapter(
                this
            )
        rv_movies.adapter = moviesAdapter
        rv_movies.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // scrolled down
                    moviesAdapter.scrollDirection = MoviesListAdapter.ScrollDirection.DOWN
                } else {
                    moviesAdapter.scrollDirection = MoviesListAdapter.ScrollDirection.UP
                }
            }
        })
        val itemTouchHelper = ItemTouchHelper(ItemTouchListenerCallback(moviesAdapter))
        itemTouchHelper.attachToRecyclerView(rv_movies)

        this.moviesList = moviesList.toMutableList()
        moviesAdapter.swapData(this.moviesList)
    }

    override fun remove(position: Int) {
        moviesList.removeAt(position)
        moviesAdapter.swapData(moviesList)
    }

    override fun addToFavs(position: Int) {
        if (moviesList[position] is Movie) {
            val movie = moviesList[position] as Movie
            Toast.makeText(this, "${movie.title} added to favs", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
