package com.meetntrain.moviesapp.common

object Constants {
    // network
    val BASE_URL = "https://api.themoviedb.org/3/"
    val API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MmE0Zjc1NjQ4NTFiNWE5MGVhYjUyODc1NDExZGQxMiIsInN1YiI6IjVlN2NiMDkxMTNhMzIwMzIxNzI2ZmYwYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pbgp58KV80gXuk3x-y9opTUmRPf6wM0I48RbHgTit2A"
    val NETWORK_TIMEOUT: Long = 30
    val IMAGE_SERVER = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"
    val BASE_URL_KOIN_KEY = "base_url"
    val IMAGE_SERVER_KOIN_KEY = "image_server"
    val AUTH_INTERCEPTOR_KOIN_KEY = "auth_interceptor"
    val AUTH_HEADER_KEY = "Authorization"
}