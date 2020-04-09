package com.meetntrain.moviesapp.features.popular_movies

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.meetntrain.moviesapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesViewModel.moviesLiveData.observe(this, Observer {
            Toast.makeText(this, it[0].title, Toast.LENGTH_SHORT).show()
        })
        moviesViewModel.getAllMovies()
    }
}
