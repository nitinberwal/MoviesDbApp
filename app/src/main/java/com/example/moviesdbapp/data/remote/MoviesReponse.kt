package com.example.moviesdbapp.data.remote

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class SearchMoviesReponse(
    val results: List<Movie>
)