package com.meetntrain.moviesapp.features.popular_movies.popular_movies.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_title
    val vote = itemView.tv_vote
    val image = itemView.iv_movie_icon
    val overview = itemView.tv_overview
}