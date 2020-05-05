package com.meetntrain.moviesapp.popular_movies.list.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.meetntrain.moviesapp.popular_movies.list.MoviesListAdapter

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun <T> bind(item: T, scrollDirection: MoviesListAdapter.ScrollDirection)
    abstract fun animateView(
        viewToAnimate: View,
        scrollDirection: MoviesListAdapter.ScrollDirection
    )
}