package com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.view_holder

import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.Constants
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.model.Movie
import com.meetntrain.moviesapp.features.popular_movies.popular_movies.list.MoviesListAdapter
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewHolder(
    itemView: View,
    private val interaction: MoviesListAdapter.Interaction?
) : BaseViewHolder(itemView) {

    init {
        itemView.apply {
            btn_remove.setOnClickListener {
                interaction?.remove(adapterPosition)
            }
            btn_fav.setOnClickListener {
                interaction?.addToFavs(adapterPosition)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun <T> bind(item: T, scrollDirection: MoviesListAdapter.ScrollDirection) =
        with(itemView) {
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
                animateView(this, scrollDirection)
                if (!item.adult) {
                    iv_plus_18.visibility = View.VISIBLE
                } else {
                    iv_plus_18.visibility = View.GONE
                }

            }
        }

    override fun animateView(
        viewToAnimate: View,
        scrollDirection: MoviesListAdapter.ScrollDirection
    ) {
        if (viewToAnimate.animation == null) {
            val animation: Animation
            if (scrollDirection == MoviesListAdapter.ScrollDirection.DOWN) {
                animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_up)
            } else {
                animation =
                    AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_from_top)

            }
            viewToAnimate.animation = animation

        }
    }
}
