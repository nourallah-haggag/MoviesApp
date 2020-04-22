package com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.view_holder

import android.view.View
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.MoviesListAdapter

class LoadingViewHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun <T> bind(item: T, scrollDirection: MoviesListAdapter.ScrollDirection) {
    }

    override fun animateView(
        viewToAnimate: View,
        scrollDirection: MoviesListAdapter.ScrollDirection
    ) {

    }
}