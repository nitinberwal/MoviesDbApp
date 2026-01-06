package com.example.moviesdbapp.searchMovies.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviesdbapp.view.MoviesViewModel
import com.example.moviesdbapp.view.commonViews.MovieItemUI

@Composable
fun SearchMoviesScreen(
    searchMoviesViewModel: SearchMoviesViewModel,
    navHostController: NavHostController,
    moviesViewModel: MoviesViewModel
) {
    var searchText = remember { mutableStateOf("") }
    val context = LocalContext.current
    val searchMoviesState = searchMoviesViewModel.searchMoviesState.collectAsState().value


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TextField(
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                value = searchText.value,
                label = { Text("Enter movie name") },
                onValueChange = { newText ->
                    searchText.value = newText
                },
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = Color.Blue, shape = RoundedCornerShape(12.dp))
                    .clickable {
                        if (searchText.value.isEmpty().not()) {
                            searchMoviesViewModel.getSearchMoviesResults(searchText.value.replace(' ', '+'))
                        } else {
                            Toast.makeText(context, "Please enter movie name", Toast.LENGTH_SHORT)
                        }
                    }
            ) {
                Text("Search", color = Color.White)
            }
        }

        if (searchMoviesState.isLoading && searchText.value.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize().padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            items(items = searchMoviesState.movieList, key = { movie -> movie?.id?:-1 }) { movie ->
                MovieItemUI(
                    movie,
                    navHostController,
                    moviesViewModel,
                    onItemClick = { })
            }
        }
    }

}