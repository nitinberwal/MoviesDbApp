package com.example.moviesdbapp.data.repo

import android.util.Log
import com.example.moviesdbapp.data.local.MoviesDatabase
import com.example.moviesdbapp.data.remote.Movie
import com.example.moviesdbapp.data.remote.MoviesApi
import com.example.moviesdbapp.domain.MoviesRepository
import com.example.moviesdbapp.utils.AppConstants
import com.example.moviesdbapp.utils.DataStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi, private val moviesDb: MoviesDatabase
): MoviesRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<DataStatus<List<Movie?>>> {
        return flow {
            emit(DataStatus.Loading(true))
            val localData = moviesDb.getMoviesDao().getMoviesBasedOnCategory(category)

            val shouldLoadLocalMovie = localData.isNullOrEmpty().not() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(DataStatus.Pass(localData))
                emit(DataStatus.Loading(false))
                return@flow
            }

            val moviesListFromApi = try {
                moviesApi.getMoviesListBasedOnType("https://api.themoviedb.org/3/movie/popular", AppConstants.API_KEY, AppConstants.ACCEPT_TYPE, page)
            } catch (ex: Exception) {
                emit(DataStatus.Fail("Error while loading movies", null))
                return@flow
            }

            moviesListFromApi?.results?.forEach { currMovie ->
                currMovie.category = category
            }
            moviesDb.getMoviesDao().insertMovies(moviesListFromApi?.results?:listOf())
            emit(DataStatus.Pass(moviesListFromApi?.results))
            emit(DataStatus.Loading(false))
        }
    }

    override suspend fun getMovieBasedOnId(id: Int?): Flow<DataStatus<Movie?>> {
        return flow {
            emit(DataStatus.Loading(true))
            val movie = moviesDb.getMoviesDao().getMovieBasedOnId(id)

            if(movie != null){
                emit(DataStatus.Pass(movie))
                emit(DataStatus.Loading(false))
                return@flow
            }
            emit(DataStatus.Fail("No such movie present"))
        }
    }

    override suspend fun getSavedMovies(): Flow<DataStatus<List<Movie?>>> {
        return flow {
            emit(DataStatus.Loading(true))
            val movie = moviesDb.getMoviesDao().getSavedMovies()

            if(movie != null){
                emit(DataStatus.Pass(movie))
                emit(DataStatus.Loading(false))
                return@flow
            }
            emit(DataStatus.Fail("No saved movies present"))
        }
    }

    override suspend fun saveUnsaveMovie(movie: Movie) {
        moviesDb.getMoviesDao().insertMovies(listOf(movie))
    }

    override suspend fun searchMovies(movieName: String): Flow<DataStatus<List<Movie?>>> {
        return flow {
            val searchMovies = try {
                moviesApi.searchMovies(
                    "https://api.themoviedb.org/3/search/movie",
                    AppConstants.API_KEY,
                    AppConstants.ACCEPT_TYPE,
                    movieName
                )
            } catch (ex: Exception) {
//                Log.d("nitin", "exception is ${ex}")
                emit(DataStatus.Fail("Error while loading movies", null))
                return@flow
            }
            emit(DataStatus.Pass(searchMovies?.results))
            emit(DataStatus.Loading(false))
        }
    }
}