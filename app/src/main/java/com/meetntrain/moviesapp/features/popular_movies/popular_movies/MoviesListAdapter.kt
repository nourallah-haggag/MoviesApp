package com.meetntrain.moviesapp.features.popular_movies.popular_movies

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.Constants
import com.meetntrain.moviesapp.common.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesListAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Movie, MoviesListAdapter.MoviesViewHolder>(
        DC()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoviesViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false), interaction
    )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun swapData(data: List<Movie>) {
        submitList(data.toMutableList())
    }

    inner class MoviesViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.apply {
                setOnClickListener(this@MoviesViewHolder)
                btn_remove.setOnClickListener {
                    interaction?.remove(adapterPosition)
                }
                btn_fav.setOnClickListener{
                    interaction?.addToFavs(adapterPosition)
                }
            }
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
            getItem(adapterPosition)
        }

        fun bind(item: Movie) = with(itemView) {
            // TODO: Bind the data with View
            tv_title.text = item.title
            tv_overview.text = item.overview
            tv_vote.text = item.voteAverage
            Glide.with(context).load(Constants.IMAGE_SERVER + item.image).into(iv_movie_icon)

        }
    }

    interface Interaction {
        fun remove(position: Int)
        fun addToFavs(position: Int)

    }

    private class DC : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return (oldItem.title == newItem.title && oldItem.overview == newItem.overview && oldItem.image == newItem.image && oldItem.voteAverage == newItem.voteAverage)
        }
    }
}