package com.meetntrain.moviesapp.features.popular_movies.paginated_movies.list

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetntrain.moviesapp.R
import com.meetntrain.moviesapp.common.model.IMainScreenModel
import com.meetntrain.moviesapp.common.model.LoadingModel
import com.meetntrain.moviesapp.common.model.Movie
import java.lang.IllegalArgumentException

class PaginatedMoviesAdapter(private val moviesList: MutableList<IMainScreenModel>) :
    RecyclerView.Adapter<BaseViewHolder>() {

    // a flag indicating that there is a progress in the footer
    private var isLoading = false

    override fun getItemViewType(position: Int): Int {
        return when (moviesList[position]) {
            is Movie -> R.layout.item_movie
            is LoadingModel -> R.layout.item_loading
            //TODO: handle error state and utilize sealed classes in this case
            else -> throw IllegalArgumentException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            R.layout.item_movie -> PaginatedMoviesViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_movie,
                    parent,
                    false
                )
            )
            R.layout.item_loading -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()

        }
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    fun addData(data: MutableList<IMainScreenModel>) {
        isLoading = false
        moviesList.addAll(data)
        notifyItemInserted(itemCount)
        notifyItemRangeInserted(itemCount, itemCount + data.size)

    }

    fun addLoadingView() {
        isLoading = true
        moviesList.add(LoadingModel())
        notifyItemInserted(itemCount - 1)
    }

    fun removeLoadingView() {
        if (isLoading) {
            if (moviesList[itemCount - 1] is LoadingModel) {
                val footer = moviesList[itemCount - 1]
                moviesList.remove(footer)
            }
        }
    }
}