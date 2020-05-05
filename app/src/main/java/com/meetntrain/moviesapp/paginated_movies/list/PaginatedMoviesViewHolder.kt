package com.meetntrain.moviesapp.paginated_movies.list

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.Constants
import com.meetntrain.moviesapp.common.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class PaginatedMoviesViewHolder(itemView: View) : BaseViewHolder(itemView) {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun <T> bind(item: T) =
        with(itemView)
        {
            if (item is Movie) {
                tv_title.text = item.title
                tv_overview.text = item.overview
                tv_vote.text = item.voteAverage
                item.image?.let {
                    Glide.with(context).load(Constants.IMAGE_SERVER + it).into(iv_movie_icon)

                }
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
            }
        }
}