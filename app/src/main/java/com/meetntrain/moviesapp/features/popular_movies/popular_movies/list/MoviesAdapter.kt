package com.meetntrain.moviesapp.features.popular_movies.popular_movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.Constants
import com.meetntrain.moviesapp.common.activity.ActivityRetriever
import com.meetntrain.moviesapp.common.model.Movie
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviesAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MoviesViewHolder>(),
    KoinComponent {
    private val activityRetriever: ActivityRetriever by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view =
            LayoutInflater.from(activityRetriever.context)
                .inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = moviesList[position]
        holder.apply {
            title.text = movieItem.title
            overview.text = movieItem.overview
            vote.text = movieItem.voteAverage
            Glide.with(activityRetriever.context).load(Constants.IMAGE_SERVER + movieItem.image)
                .into(image)
        }
    }
}