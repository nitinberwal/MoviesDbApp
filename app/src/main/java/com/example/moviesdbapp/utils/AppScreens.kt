package com.example.moviesdbapp.utils

sealed class AppScreens(val route:String) {
    object Home: AppScreens("home")
    object PopularMoviesList: AppScreens("popularMovieList")
    object NowPlayingMovies: AppScreens("nowPlayingMovies")
    object DetailsScreen: AppScreens("detailsScreen")
    object SavedMovies: AppScreens("savedMovies")
    object SearchMovies: AppScreens("searchMovies")

}