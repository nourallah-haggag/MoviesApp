package com.meetntrain.moviesapp.paginated_movies

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.activity.BaseActivity
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.utils.toggleLoadingState
import com.meetntrain.moviesapp.paginated_movies.list.PaginatedMoviesAdapter
import kotlinx.android.synthetic.main.activity_paginated_movies.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaginatedMoviesActivity : BaseActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val moviesViewModel: PaginatedMoviesViewModel by viewModel()

    // recycler view
    private lateinit var moviesAdapter: PaginatedMoviesAdapter
    private var moviesList = mutableListOf<IMainScreenModel>()

    // keep a counter of the current page
    private var pageNo = 1


    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paginated_movies)
        setupMoviesRecyclerView()
        moviesViewModel.getAllMovies(pageNo)

        observeMovie<List<IMainScreenModel>>(
            liveData = moviesViewModel.channelLiveData,
            success = { movies ->
                moviesAdapter.removeLoadingView()
                // load the data into the recycler view
                moviesAdapter.addData(data = movies.toMutableList())
                moviesAdapter.addLoadingView()

            },
            loading = { isLoading ->
                if (pageNo == 1) {
                    toggleLoadingState(
                        isLoading = isLoading,
                        loadingView = progress_paginated_loading
                    )
                }
            }, error = {
                //TODO: handle error
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
