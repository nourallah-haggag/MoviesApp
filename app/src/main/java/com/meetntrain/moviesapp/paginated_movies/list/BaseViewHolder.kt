package com.meetntrain.moviesapp.paginated_movies.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun <T> bind(item: T)
}