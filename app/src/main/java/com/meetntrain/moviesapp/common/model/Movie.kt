package com.meetntrain.moviesapp.common.model

import com.squareup.moshi.Json

data class Movie(
    val title: String,
    @Json(name = "vote_average") val voteAverage: String,
    val overview: String,
    @Json(name = "poster_path") val image: String,
    val adult:Boolean
)