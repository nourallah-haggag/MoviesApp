package com.meetntrain.moviesapp.features.popular_movies.paginated_movies.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun <T> bind(item: T)
}