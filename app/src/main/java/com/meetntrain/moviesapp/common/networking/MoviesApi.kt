package com.meetntrain.moviesapp.common.networking

import com.meetntrain.moviesapp.common.model.response.ActorResponse
import com.meetntrain.moviesapp.common.model.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    suspend fun getMoviesList(): MoviesResponse

    @GET("person/popular")
    suspend fun getActorsList(): ActorResponse

    @GET("discover/movie")
    suspend fun getPaginatedMovies(@Query("page") pageNo:Int): MoviesResponse
}