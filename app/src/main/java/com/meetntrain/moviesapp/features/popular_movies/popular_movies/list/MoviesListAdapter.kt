package com.meetntrain.moviesapp.features.popular_movies.popular_movies.list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.model.Actor
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.view_holder.ActorsViewHolder
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.view_holder.BaseViewHolder
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.view_holder.MoviesViewHolder

class MoviesListAdapter(private val interaction: Interaction? = null) :
    ListAdapter<IMainScreenModel, BaseViewHolder>(
        DC()
    ) {

    var scrollDirection =
        ScrollDirection.DOWN

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == R.layout.item_movie) {

            return MoviesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie, parent, false), interaction
            )
        } else {
            return ActorsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_actor, parent, false), interaction
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) is Movie) {
            return R.layout.item_movie
        } else if (getItem(position) is Actor) {
            return R.layout.item_actor
        } else
            throw IllegalArgumentException()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position), scrollDirection)
    }

    fun swapData(data: List<IMainScreenModel>) {
        submitList(data.toMutableList())
    }


    interface Interaction {
        fun remove(position: Int)
        fun addToFavs(position: Int)

    }

    private class DC : DiffUtil.ItemCallback<IMainScreenModel>() {
        override fun areItemsTheSame(
            oldItem: IMainScreenModel,
            newItem: IMainScreenModel
        ): Boolean {
            if (oldItem is Movie && newItem is Movie)
                return oldItem.title == newItem.title
            else
                if (oldItem is Actor && newItem is Actor)
                    return oldItem.name == newItem.name
                else
                    return false
        }

        override fun areContentsTheSame(
            oldItem: IMainScreenModel,
            newItem: IMainScreenModel
        ): Boolean {
            if (oldItem is Movie && newItem is Movie)

                return (oldItem.title == newItem.title && oldItem.overview == newItem.overview && oldItem.image == newItem.image && oldItem.voteAverage == newItem.voteAverage)
            else if (oldItem is Actor && newItem is Actor)
                return (oldItem.name == newItem.name && oldItem.knownFor == newItem.knownFor
                        && oldItem.img == newItem.img)
            else
                return false
        }
    }


    enum class ScrollDirection {
        UP, DOWN
    }
}