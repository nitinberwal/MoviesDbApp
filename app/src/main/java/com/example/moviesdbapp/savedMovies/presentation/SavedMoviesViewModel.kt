package com.example.moviesdbapp.savedMovies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdbapp.domain.MoviesRepository
import com.example.moviesdbapp.utils.DataStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedMoviesViewModel(private val moviesRepository: MoviesRepository): ViewModel() {
    private var _savedMoviesState = MutableStateFlow(MoviesState())
    val savedMoviesState = _savedMoviesState.asStateFlow()

    init {
        getSavedMovies()
    }

    fun getSavedMovies(){
        viewModelScope.launch {
            _savedMoviesState.update {
                it.copy(isLoading = true)
            }

            moviesRepository.getSavedMovies().collectLatest { results ->
                when(results){
                    is DataStatus.Fail -> {
                        _savedMoviesState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is DataStatus.Loading -> {
                        _savedMoviesState.update {
                            it.copy(isLoading = results.isLoading)
                        }
                    }
                    is DataStatus.Pass -> {
                        results.data?.let { movie ->
                            _savedMoviesState.update {
                                it.copy(movieList = movie)
                            }
                        }
                    }
                }
            }
        }
    }

}