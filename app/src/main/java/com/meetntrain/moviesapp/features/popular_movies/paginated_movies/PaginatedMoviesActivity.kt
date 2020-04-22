package com.meetntrain.moviesapp.features.popular_movies.paginated_movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.utils.toggleLoadingState
import com.meetntrain.moviesapp.common.view_model.State
import com.meetntrain.moviesapp.features.popular_movies.paginated_movies.list.PaginatedMoviesAdapter
import com.meetntrain.moviesapp.features.popular_movies.paginated_movies.list.PaginatedMoviesViewHolder
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.MoviesViewModel
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.MoviesListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_paginated_movies.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaginatedMoviesActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val moviesViewModel: PaginatedMoviesViewModel by viewModel()

    // recycler view
    private lateinit var moviesAdapter: PaginatedMoviesAdapter
    private var moviesList = mutableListOf<IMainScreenModel>()

    // keep a counter of the current page
    //TODO: keep the counter in the adapter of the recycler view
    private var pageNo = 1


    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paginated_movies)
        setupMoviesRecyclerView()
        moviesViewModel.getAllMovies(pageNo)

        moviesViewModel.channelLiveData.observe(this, Observer { state ->
            when (state) {
                is State.LoadingState -> if (pageNo == 1) {
                    // TODO: add footer loading
                    toggleLoadingState(
                        isLoading = state.isLoading,
                        loadingView = progress_paginated_loading
                    )
                }
                is State.PresentingState<*> -> {
                    moviesAdapter.removeLoadingView()
                    val data = state.data as List<IMainScreenModel>
                    data.apply {
                        // load the data into the recycler view
                        moviesAdapter.addData(data = this.toMutableList())
                        moviesAdapter.addLoadingView()
                    }
                }
            }
        })
    }


    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupMoviesRecyclerView() {
        moviesAdapter = PaginatedMoviesAdapter(moviesList)
        rv_paginated_movies.adapter = moviesAdapter
        //TODO: add on scroll listener when reaching to detect when reaching bottom
        rv_paginated_movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(0)) {
                    pageNo++
                    moviesViewModel.getAllMovies(pageNo)

                }
            }
        })
    }
}
