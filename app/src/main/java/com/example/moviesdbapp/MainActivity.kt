package com.example.moviesdbapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.moviesdbapp.data.remote.Movie
import com.example.moviesdbapp.ui.theme.MoviesDbAppTheme
import com.example.moviesdbapp.utils.AppScreens
import com.example.moviesdbapp.view.HomeScreen
import com.example.moviesdbapp.details.presentation.MovieDetailsScreen
import com.example.moviesdbapp.details.presentation.MoviesDetailsViewModel
import com.example.moviesdbapp.savedMovies.presentation.SavedMoviesViewModel
import com.example.moviesdbapp.searchMovies.view.SearchMoviesViewModel
import com.example.moviesdbapp.view.MoviesViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val moviesViewModel: MoviesViewModel by viewModel()
    private val moviesDetailsViewModel: MoviesDetailsViewModel by viewModel()
    private val savedMoviesViewModel: SavedMoviesViewModel by viewModel()
    private val searchMoviesViewModel: SearchMoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesDbAppTheme {
                SetBarColor(color = MaterialTheme.colorScheme.inverseSurface)
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = AppScreens.Home.route){
                        composable(AppScreens.Home.route){
                            HomeScreen(navController, moviesViewModel, savedMoviesViewModel, searchMoviesViewModel)
                        }
                        composable(AppScreens.DetailsScreen.route + "/{movieId}",
                            arguments = listOf(navArgument("movieId"){ type = NavType.IntType}),
                            deepLinks = listOf(
                                navDeepLink {
                                    uriPattern = "https://www.moviesdbapp.com/movie/{movieId}"
                                }
                            )) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt("movieId")
                            MovieDetailsScreen(movieId, backStackEntry, moviesDetailsViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SetBarColor(color:Color){
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = color) {
        systemUiController.setSystemBarsColor(color)
    }
}