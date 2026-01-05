package com.example.moviesdbapp.searchMovies.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdbapp.domain.MoviesRepository
import com.example.moviesdbapp.savedMovies.presentation.MoviesState
import com.example.moviesdbapp.utils.DataStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchMoviesViewModel(private val moviesRepository: MoviesRepository): ViewModel() {
    private var _searchMoviesState = MutableStateFlow(MoviesState())
    val searchMoviesState = _searchMoviesState.asStateFlow()

    fun getSearchMoviesResults(movieName:String){
        viewModelScope.launch {
            _searchMoviesState.update {
                it.copy(isLoading = true)
            }

            moviesRepository.searchMovies(movieName).collectLatest { results ->
                when(results){
                    is DataStatus.Fail -> {
                        _searchMoviesState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is DataStatus.Loading -> {
                        _searchMoviesState.update {
                            it.copy(isLoading = results.isLoading)
                        }
                    }
                    is DataStatus.Pass -> {
                        results.data?.let { movie ->
                            _searchMoviesState.update {
                                it.copy(movieList = movie)
                            }
                        }
                    }
                }
            }
        }
    }
}