package com.example.moviesdbapp.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdbapp.domain.MoviesRepository
import com.example.moviesdbapp.utils.DataStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesDetailsViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private var _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    fun getMovie(movieId: Int?){
        viewModelScope.launch {
            _detailsState.update {
                it.copy(isLoading = true)
            }

            moviesRepository.getMovieBasedOnId(movieId).collectLatest { results ->
                when(results){
                    is DataStatus.Fail -> {
                        _detailsState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is DataStatus.Loading -> {
                        _detailsState.update {
                            it.copy(isLoading = results.isLoading)
                        }
                    }
                    is DataStatus.Pass -> {
                        results.data?.let { movie ->
                            _detailsState.update {
                                it.copy(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }

}