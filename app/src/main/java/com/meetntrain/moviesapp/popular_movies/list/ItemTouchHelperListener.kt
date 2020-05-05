package com.meetntrain.moviesapp.popular_movies.list

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {
    fun onItemMoved(recycler: RecyclerView, fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(recycler: RecyclerView.ViewHolder, position: Int)
}