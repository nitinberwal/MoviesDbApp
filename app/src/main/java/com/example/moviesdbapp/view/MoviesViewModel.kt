package com.example.moviesdbapp.view

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviesdbapp.data.remote.Movie
import com.example.moviesdbapp.domain.MoviesRepository
import com.example.moviesdbapp.utils.Category
import com.example.moviesdbapp.utils.DataStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class MoviesViewModel(private val moviesRepository: MoviesRepository): ViewModel() {
    private var _movieListState = MutableStateFlow(MoviesListState())
    val movieListState: StateFlow<MoviesListState> = _movieListState.asStateFlow()

//    init {
//        getPopularMovies(false)
//        getNowPlayingMovies(false)
//    }

    fun onEvent(event: MoviesListUiEvent, currScreen:Int) {
        when (event) {
            is MoviesListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(currentScreen = currScreen)
                }
            }

            is MoviesListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovies(true)
                } else if(event.category == Category.NOW_PLAYING){
                    getNowPlayingMovies(true)
                }
            }
        }
    }


    fun getPopularMovies(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            moviesRepository.getMovieList(forceFetchFromRemote, Category.POPULAR,  movieListState.value.popularMoviesListPage)
                .collectLatest { res ->
                    when(res){
                        is DataStatus.Fail -> {
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }
                        is DataStatus.Pass -> {
                            res.data?.let { data->
                                _movieListState.update {
                                    it.copy(popularMoviesList = movieListState.value.popularMoviesList + data.shuffled(), isLoading = false,
                                        popularMoviesListPage = movieListState.value.popularMoviesListPage + 1)
                                }
                            }
                        }
                        is DataStatus.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = res.isLoading)
                            }
                        }
                    }
                }
        }
    }

    fun getNowPlayingMovies(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            moviesRepository.getMovieList(forceFetchFromRemote, Category.NOW_PLAYING,  movieListState.value.nowPlayingMoviesListPage)
                .collectLatest { res ->
                    when(res){
                        is DataStatus.Fail -> {
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }
                        is DataStatus.Pass -> {
                            res.data?.let { data ->
                                _movieListState.update {
                                    it.copy(nowPlayingMoviesList = movieListState.value.nowPlayingMoviesList + data.shuffled(), isLoading = false,
                                        nowPlayingMoviesListPage = movieListState.value.nowPlayingMoviesListPage + 1)
                                }
                            }
                        }
                        is DataStatus.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = res.isLoading)
                            }
                        }
                    }
                }
        }
    }

    fun saveUnsaveMovie(movie:Movie){
        viewModelScope.launch {
            moviesRepository.saveUnsaveMovie(movie)
        }
    }
}