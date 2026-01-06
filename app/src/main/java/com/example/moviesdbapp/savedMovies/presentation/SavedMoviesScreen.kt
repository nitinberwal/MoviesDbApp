package com.example.moviesdbapp.savedMovies.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviesdbapp.view.MoviesListState
import com.example.moviesdbapp.view.MoviesViewModel
import com.example.moviesdbapp.view.commonViews.MovieItemUI

@Composable
fun SavedMoviesScreen(
    savedMoviesViewModel: SavedMoviesViewModel,
    navHostController: NavHostController,
    moviesViewModel: MoviesViewModel,
    moviesListState: MoviesListState
) {
    val savedMoviesState = savedMoviesViewModel.savedMoviesState.collectAsState().value

    LaunchedEffect(key1 =  Unit) {
        savedMoviesViewModel.getSavedMovies()
    }

    if(savedMoviesState.movieList?.isNullOrEmpty() == true){
        if(savedMoviesState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text="No saved jobs", fontSize = 24.sp, color = Color.Black)
            }
        }
    }
    else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(savedMoviesState.movieList.size) { index ->
                MovieItemUI(savedMoviesState.movieList[index],
                    navHostController,
                    moviesViewModel,
                    onItemClick = { clickedMovie ->
                        moviesListState.savedMoviesList = moviesListState.savedMoviesList.map {
                            if (it?.id == clickedMovie.id) {
                                it?.copy(isSaved = !it.isSaved)
                            } else it
                        }
                    })
//                if(index >= savedMoviesState.movieList.size -1 && savedMoviesState.isLoading.not()){
//                    onEvent(MoviesListUiEvent.Paginate(Category.POPULAR))
//                }
            }
        }
    }

}