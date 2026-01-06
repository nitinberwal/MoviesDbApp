package com.example.moviesdbapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.SaveAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesdbapp.R
import com.example.moviesdbapp.savedMovies.presentation.SavedMoviesScreen
import com.example.moviesdbapp.savedMovies.presentation.SavedMoviesViewModel
import com.example.moviesdbapp.searchMovies.view.SearchMoviesScreen
import com.example.moviesdbapp.searchMovies.view.SearchMoviesViewModel
import com.example.moviesdbapp.utils.AppConstants
import com.example.moviesdbapp.utils.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    savedMoviesViewModel: SavedMoviesViewModel,
    searchMoviesViewModel: SearchMoviesViewModel
) {
    val movieState = moviesViewModel.movieListState.collectAsState()
    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavigationBar(bottomNavController, moviesViewModel::onEvent)
    }, topBar = {
        TopAppBar(title = {
            Text(text = if(movieState.value.currentScreen == AppConstants.POPULAR_MOVIES_LIST_SCREEN) getString(
                LocalContext.current, R.string.popularMovies)
            else if(movieState.value.currentScreen == AppConstants.NOW_PLAYING_MOVIES_LIST_SCREEN) getString(
                LocalContext.current, R.string.nowPlayingMovie)
                else if(movieState.value.currentScreen == AppConstants.SAVED_MOVIES_LIST_SCREEN) getString(
                LocalContext.current, R.string.savedMovies)
                else getString(LocalContext.current, R.string.searchMovies),
                fontSize = 24.sp)
        }, modifier = Modifier.shadow(2.dp), colors = TopAppBarDefaults.topAppBarColors(
            MaterialTheme.colorScheme.inverseOnSurface)
        )
    }) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            NavHost(navController = bottomNavController,
                startDestination = AppScreens.PopularMoviesList.route){
                composable(AppScreens.PopularMoviesList.route) {
                    PopularMoviesScreen(moviesListState = movieState.value, navHostController = navController, onEvent = moviesViewModel::onEvent, moviesViewModel)
                }
                composable(AppScreens.NowPlayingMovies.route) {
                    NowPlayingMoviesScreen(moviesListState = movieState.value, navHostController = navController, onEvent = moviesViewModel::onEvent, moviesViewModel)
                }
                composable(AppScreens.SavedMovies.route) {
                    SavedMoviesScreen(savedMoviesViewModel, navController, moviesViewModel, movieState.value)
                }
                composable(route = AppScreens.SearchMovies.route) {
                    SearchMoviesScreen(searchMoviesViewModel, navController, moviesViewModel)
                }
            }
        }
    }
}

@Composable
    fun BottomNavigationBar(bottomNavController: NavHostController, onEvent: (MoviesListUiEvent, currScreen:Int) -> Unit){
        val items = listOf<BottomItem>(
            BottomItem(getString(LocalContext.current, R.string.popular), Icons.Rounded.Movie),
            BottomItem(getString(LocalContext.current, R.string.nowPlaying), Icons.Rounded.PlayCircle),
            BottomItem(getString(LocalContext.current, R.string.search), Icons.Rounded.Search),
            BottomItem(getString(LocalContext.current, R.string.saved), Icons.Rounded.SaveAlt)
        )

        val selected = rememberSaveable {
            mutableIntStateOf(0)
        }

        NavigationBar {
            Row(modifier = Modifier.background(Color.LightGray)){
                items.forEachIndexed { index, item ->
                    NavigationBarItem(selected = selected.value == index,
                        onClick = {
                        selected.value = index
                        when(selected.value){
                            0 -> {
                                onEvent(MoviesListUiEvent.Navigate, AppConstants.POPULAR_MOVIES_LIST_SCREEN)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(AppScreens.PopularMoviesList.route)
                            }
                            1 -> {
                                onEvent(MoviesListUiEvent.Navigate, AppConstants.NOW_PLAYING_MOVIES_LIST_SCREEN)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(AppScreens.NowPlayingMovies.route)
                            }
                            2 -> {
                                onEvent(MoviesListUiEvent.Navigate, AppConstants.SEARCH_SCREEN)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(AppScreens.SearchMovies.route)
                            }
                            3 -> {
                                onEvent(MoviesListUiEvent.Navigate, AppConstants.SAVED_MOVIES_LIST_SCREEN)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(AppScreens.SavedMovies.route)
                            }
                        }

                    },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        label = {
                            Text(text = item.title, color = MaterialTheme.colorScheme.onBackground)
                        })
                }
            }
        }
    }

data class BottomItem(
    val title: String,
    val icon: ImageVector
)