package com.example.moviesdbapp.details.presentation

import com.example.moviesdbapp.data.remote.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
