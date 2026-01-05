package com.example.moviesdbapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdbapp.data.remote.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertMovies(movieList:List<Movie>)

    @Query("Select * from movies where id=:id")
    suspend fun getMovieBasedOnId(id:Int?):Movie

    @Query("Select * from movies where isSaved=1")
    suspend fun getSavedMovies(): List<Movie>?
    @Query("Select * from movies where category=:category")
    suspend fun getMoviesBasedOnCategory(category:String?):List<Movie>?


    @Query("Select MAX(page) from movies")
    suspend fun getLastPage():Int?

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}