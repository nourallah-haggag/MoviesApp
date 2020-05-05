package com.meetntrain.moviesapp.popular_movies

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.activity.BaseActivity
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.common.utils.toggleLoadingState
import com.meetntrain.moviesapp.paginated_movies.PaginatedMoviesActivity
import com.meetntrain.moviesapp.popular_movies.list.MoviesListAdapter
import com.meetntrain.moviesapp.popular_movies.list.view_holder.ItemTouchListenerCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MoviesActivity : BaseActivity(), MoviesListAdapter.Interaction {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private lateinit var moviesList: MutableList<IMainScreenModel>

    // recycler view
    private lateinit var moviesAdapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_goto_paginated.setOnClickListener {
            val intent = Intent(this, PaginatedMoviesActivity::class.java)
            startActivity(intent)
        }

        moviesViewModel.getAllMovies()
        observeMovie<List<Movie>>(
            liveData = moviesViewModel.channelLiveData,
            success = { movies ->
                setupMoviesRecyclerView(movies)
            },
            loading = { isLoading ->
                toggleLoadingState(isLoading = isLoading, loadingView = progress_bar)
                Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }, error = {
                //TODO: handle error
            })
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
