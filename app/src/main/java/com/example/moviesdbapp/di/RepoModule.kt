package com.example.moviesdbapp.di

import com.example.moviesdbapp.data.local.MoviesDatabase
import com.example.moviesdbapp.data.remote.MoviesApi
import com.example.moviesdbapp.data.repo.MoviesRepositoryImpl
import com.example.moviesdbapp.domain.MoviesRepository
import org.koin.dsl.module

val repoModule = module {
    single { MoviesRepositoryImpl(get(), get()) }
    single { provideMoviesRepositoryImpl(get(), get()) }
}

fun provideMoviesRepositoryImpl(moviesApi: MoviesApi, moviesDb: MoviesDatabase): MoviesRepository =
    MoviesRepositoryImpl(moviesApi, moviesDb)