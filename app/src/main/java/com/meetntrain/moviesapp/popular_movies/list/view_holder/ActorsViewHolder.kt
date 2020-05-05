package com.meetntrain.moviesapp.popular_movies.list.view_holder

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.Constants
import com.meetntrain.moviesapp.common.model.Actor
import com.meetntrain.moviesapp.popular_movies.list.MoviesListAdapter
import kotlinx.android.synthetic.main.item_actor.view.*

class ActorsViewHolder(
    itemView: View,
    private val interaction: MoviesListAdapter.Interaction?
) : BaseViewHolder(itemView) {


    override fun <T> bind(item: T, scrollDirection: MoviesListAdapter.ScrollDirection) =
        // TODO: Bind the data with View
        with(itemView) {
            if (item is Actor) {
                tv_actor.text = item.name + " " + item.knownFor
                iv_actor_icon.apply {
                    item.img?.let {
                        Glide.with(context).load(Constants.IMAGE_SERVER + it).into(this)
                    }

                }
                animateView(this, scrollDirection = scrollDirection)
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