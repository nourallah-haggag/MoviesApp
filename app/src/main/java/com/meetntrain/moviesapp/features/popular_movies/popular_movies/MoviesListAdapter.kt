package com.meetntrain.moviesapp.features.popular_movies.popular_movies

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.Constants
import com.meetntrain.moviesapp.common.model.Actor
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.model.Movie
import kotlinx.android.synthetic.main.item_actor.view.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.item_movie.view.tv_overview
import java.lang.IllegalArgumentException

class MoviesListAdapter(private val interaction: Interaction? = null) :
    ListAdapter<IMainScreenModel, RecyclerView.ViewHolder>(
        DC()
    ) {

    var scrollDirection = ScrollDirection.DOWN

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoviesViewHolder)
            holder.bind(getItem(position))
        else if (holder is ActorsViewHolder) {
            holder.bind(getItem(position))
        } else {
            throw IllegalArgumentException()
        }
    }

    fun swapData(data: List<IMainScreenModel>) {
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
                btn_fav.setOnClickListener {
                    interaction?.addToFavs(adapterPosition)
                }
            }
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
            getItem(adapterPosition)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(item: IMainScreenModel) = with(itemView) {
            // TODO: Bind the data with View
            if (item is Movie) {

                tv_title.text = item.title
                tv_overview.text = item.overview
                tv_vote.text = item.voteAverage
                Glide.with(context).load(Constants.IMAGE_SERVER + item.image).into(iv_movie_icon)
                iv_film_status.apply {
                    if (item.voteAverage.toDouble() < 8) {
                        setImageDrawable(context.getDrawable(R.drawable.ic_poop))
                    } else {
                        setImageDrawable(
                            context.getDrawable(
                                R.drawable
                                    .ic_fire
                            )
                        )
                    }
                }
                animateView(this)
                if (!item.adult) {
                    iv_plus_18.visibility = View.VISIBLE
                } else {
                    iv_plus_18.visibility = View.GONE
                }

            }
        }
    }

    inner class ActorsViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.apply {
                setOnClickListener(this@ActorsViewHolder)
            }
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
            getItem(adapterPosition)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(item: IMainScreenModel) = with(itemView) {
            // TODO: Bind the data with View
            if (item is Actor) {
                tv_actor.text = item.name + " " + item.knownFor
                iv_actor_icon.apply {
                    Glide.with(context).load(Constants.IMAGE_SERVER + item.img).into(this)

                }
                animateView(this)
            }
        }
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

    private fun animateView(viewToAnimate: View) {
        if (viewToAnimate.animation == null) {
            val animation: Animation
            if (scrollDirection == ScrollDirection.DOWN) {
                animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_up)
            } else {
                animation =
                    AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_from_top)

            }
            viewToAnimate.animation = animation

        }
    }

    enum class ScrollDirection {
        UP, DOWN
    }
}