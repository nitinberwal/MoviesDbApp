package com.example.moviesdbapp.domain

import com.example.moviesdbapp.data.remote.Movie
import com.example.moviesdbapp.utils.DataStatus
import kotlinx.coroutines.flow.Flow

interface MoviesRepository

{

    suspend fun getMovieList(
        forceFetchFromRemote:Boolean,
        category: String,
        page: Int
    ): Flow<DataStatus<List<Movie?>>>

    suspend fun getMovieBasedOnId(id: Int?): Flow<DataStatus<Movie?>>

    suspend fun getSavedMovies(): Flow<DataStatus<List<Movie?>>>
    suspend fun saveUnsaveMovie(movie: Movie)

    suspend fun searchMovies(movieName:String): Flow<DataStatus<List<Movie?>>>

}