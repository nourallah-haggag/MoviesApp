package com.meetntrain.moviesapp.common.di

import com.meetntrain.moviesapp.common.repo.MoviesRepo
import org.koin.dsl.module

val repoModule = module {
    factory { MoviesRepo(get()) }
}