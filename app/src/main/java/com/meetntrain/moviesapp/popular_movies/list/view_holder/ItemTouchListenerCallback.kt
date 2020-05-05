package com.meetntrain.moviesapp.popular_movies.list.view_holder

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.meetntrain.moviesapp.popular_movies.list.ItemTouchHelperListener

class ItemTouchListenerCallback(private val listener: ItemTouchHelperListener) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean = true
    override fun isItemViewSwipeEnabled(): Boolean = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.END)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return listener.onItemMoved(
            recyclerView,
            viewHolder.adapterPosition,
            target.adapterPosition
        )
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemDismiss(viewHolder , viewHolder.adapterPosition)
    }
}