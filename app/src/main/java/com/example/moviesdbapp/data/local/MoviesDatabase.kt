package com.example.moviesdbapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesdbapp.data.remote.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(MoviesConverter::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MovieDao
}