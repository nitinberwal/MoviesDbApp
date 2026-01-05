package com.example.moviesdbapp.view

sealed class MoviesListUiEvent {
    data class Paginate(val category:String): MoviesListUiEvent()
    object Navigate: MoviesListUiEvent()
}