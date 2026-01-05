package com.example.moviesdbapp.data.local

import androidx.room.TypeConverter
import com.example.moviesdbapp.data.remote.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoviesConverter {
    @TypeConverter
    fun listToString(value: List<Movie>):String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToList(value:String): List<Movie> {
        val listType = object : TypeToken<List<Movie>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun intListToString(value: List<Int>):String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToIntList(value:String):List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}