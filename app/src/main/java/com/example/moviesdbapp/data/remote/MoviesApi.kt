package com.example.moviesdbapp.data.remote

import com.example.moviesdbapp.JsonConstants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface MoviesApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val IMAGE_BASE_URL = "https://media.themoviedb.org/t/p/w260_and_h390_face/"

    }

    @GET
    suspend fun getMoviesListBasedOnType(
        @Url url: String,
        @Header(JsonConstants.Companion.Authorization) auth: String,
        @Header(JsonConstants.Companion.Accept) accept: String,
        @Query(JsonConstants.Companion.Page) page: Int
    ): MoviesResponse?

    @GET
    suspend fun searchMovies(
        @Url url: String,
        @Header(JsonConstants.Companion.Authorization) auth: String,
        @Header(JsonConstants.Companion.Accept) accept: String,
        @Query(JsonConstants.Companion.QUERY) query: String
    ): SearchMoviesReponse?

}