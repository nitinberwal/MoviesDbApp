package com.example.moviesdbapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviesdbapp.utils.AppConstants
import com.example.moviesdbapp.utils.Category
import com.example.moviesdbapp.view.commonViews.MovieItemUI

@Composable
fun NowPlayingMoviesScreen(
    moviesListState: MoviesListState,
    navHostController: NavHostController,
    onEvent: (MoviesListUiEvent, Int) -> Unit,
    moviesViewModel: MoviesViewModel
) {

    if(moviesListState.nowPlayingMoviesList.isEmpty()){
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
            items(moviesListState.nowPlayingMoviesList.size) { index ->
                MovieItemUI(
                    moviesListState.nowPlayingMoviesList[index],
                    navHostController,
                    moviesViewModel,
                    onItemClick = { clickedMovie ->
                        moviesListState.nowPlayingMoviesList = moviesListState.nowPlayingMoviesList.map {
                            if (it?.id == clickedMovie.id) {
                                it?.copy(isSaved = !it.isSaved)
                            } else it
                        }
                    }
                )
                if(index >= moviesListState.nowPlayingMoviesList.size -1 && moviesListState.isLoading.not()){
                    onEvent(MoviesListUiEvent.Paginate(Category.NOW_PLAYING), AppConstants.NOW_PLAYING_MOVIES_LIST_SCREEN)

                }
            }
        }
    }
}
