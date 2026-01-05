package com.example.moviesdbapp.savedMovies.presentation

import com.example.moviesdbapp.data.remote.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val movieList: List<Movie?> = listOf()
)