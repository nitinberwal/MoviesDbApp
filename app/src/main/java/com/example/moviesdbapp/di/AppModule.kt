package com.example.moviesdbapp.di

import androidx.room.Room
import com.example.moviesdbapp.data.local.MoviesDatabase
import com.example.moviesdbapp.utils.AppConstants
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(get(), klass = MoviesDatabase::class.java, name= AppConstants.MOVIES_DB).build()
    }
}