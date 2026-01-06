package com.example.moviesdbapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviesdbapp.utils.AppConstants
import com.example.moviesdbapp.utils.Category
import com.example.moviesdbapp.view.commonViews.MovieItemUI

@Composable
fun PopularMoviesScreen(
    moviesListState: MoviesListState,
    navHostController: NavHostController,
    onEvent: (MoviesListUiEvent, Int) -> Unit,
    moviesViewModel: MoviesViewModel
) {
    LaunchedEffect(key1 = Unit) {
        moviesViewModel.getPopularMovies(false)
    }

    if(moviesListState.popularMoviesList.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = moviesListState.popularMoviesList, key = { movie -> movie?.id?:-1 }) { movie ->
                MovieItemUI(
                    movie,
                    navHostController,
                    moviesViewModel,
                    onItemClick = { clickedMovie ->
                        moviesListState.popularMoviesList = moviesListState.popularMoviesList.map {
                            if (it?.id == clickedMovie.id) {
                                it?.copy(isSaved = !it.isSaved)
                            } else it
                        }
                    }
                )
                if(moviesListState.popularMoviesList.indexOf(movie) >= moviesListState.popularMoviesList.size - 5 && moviesListState.isLoading.not()){
                    onEvent(MoviesListUiEvent.Paginate(Category.POPULAR), AppConstants.POPULAR_MOVIES_LIST_SCREEN)

                }
            }
        }
    }
}
