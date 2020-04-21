package com.meetntrain.moviesapp.common.model.response

import com.meetntrain.moviesapp.common.model.Actor
import com.meetntrain.moviesapp.common.model.Movie
import com.squareup.moshi.Json

data class ActorResponse(
    val page: Int,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val actorList:List<Actor>
)
