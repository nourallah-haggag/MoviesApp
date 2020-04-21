package com.meetntrain.moviesapp.common.networking

import com.meetntrain.moviesapp.common.model.response.ActorResponse
import com.meetntrain.moviesapp.common.model.response.MoviesResponse
import retrofit2.http.GET

interface MoviesApi {

    @GET("discover/movie")
    suspend fun getMoviesList(): MoviesResponse

    @GET("person/popular")
    suspend fun getActorsList(): ActorResponse
}