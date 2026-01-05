package com.example.moviesdbapp.view

import com.example.moviesdbapp.data.remote.Movie
import com.example.moviesdbapp.utils.AppConstants

data class MoviesListState(
    val isLoading:Boolean = false,
    val popularMoviesListPage:Int = 1,
    val nowPlayingMoviesListPage:Int = 1,
    val currentScreen:Int = AppConstants.POPULAR_MOVIES_LIST_SCREEN,

    var popularMoviesList:List<Movie?> = listOf(),
    var nowPlayingMoviesList:List<Movie?> = listOf(),
    var savedMoviesList: List<Movie?> = listOf()
    )