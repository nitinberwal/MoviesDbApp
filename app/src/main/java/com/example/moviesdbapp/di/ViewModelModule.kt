package com.example.moviesdbapp.di

import com.example.moviesdbapp.details.presentation.MoviesDetailsViewModel
import com.example.moviesdbapp.savedMovies.presentation.SavedMoviesViewModel
import com.example.moviesdbapp.searchMovies.view.SearchMoviesViewModel
import com.example.moviesdbapp.view.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { MoviesDetailsViewModel(get()) }
    viewModel { SavedMoviesViewModel(get()) }
    viewModel { SearchMoviesViewModel(get()) }
}