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
                            Log.d("nitin", "movieId=${movieId}")
                            MovieDetailsScreen(movieId, backStackEntry, moviesDetailsViewModel)
                        }
                    }

//                    MoviesListingScreen(
////                        moviesList = moviesList.value,
//                        modifier = Modifier.padding(innerPadding),
//                        moviesViewModel
//                    )
//                    LaunchedEffect(key1 = null) {
//                        scope.launch {
//                            moviesViewModel.getMoviesListBasedOnType(
//                                url = "now_playing",
//                                auth = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzcyNTNhZTY1OWFkMWQ5MTQyZWI1Yjg3OTU1OTFlYiIsIm5iZiI6MTc2NzE2NDA3NC43OTQsInN1YiI6IjY5NTRjOGFhN2MxYzg3NDk2ZWMzODQ0YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1nG4ITg4LDhz5scMG0_eTSocuVz70GoTFAc9yLHrJcc",
//                                accept = "application/json"
//                            )
//                        }
//                    }
//                    LaunchedEffect(key1 = null) {
//                        Handler().postDelayed({
//                            Log.d("nitin", moviesViewModel.moviesList.toString())
//                            moviesList.value = moviesList.value + moviesViewModel.moviesList.value
//                        }, 5000)
//                    }
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

@Composable
fun MoviesListingScreen(
//    moviesList: List<Movie>,
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel
) {
    val movies = listOf<Movie>()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies.size) { index ->
//            MovieItemUI(movies[index])
        }

        // Handle Loading and Error states
//        when (val state = movies.loadState.append) {
//            is LoadState.Loading -> {
//                item { CircularProgressIndicator() }
//            }
//            is LoadState.Error -> {
//                item { ErrorRetryButton(onClick = { movies.retry() }) }
//            }
//            else -> {}
//        }
//
//        movies.apply {
//            when (loadState.append) {
//                is LoadState.Loading -> {
//                    item {
//                        CircularProgressIndicator(
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                }
//
//                is LoadState.Error -> {
//                    item {
//                        Text(
//                            text = "Error loading more movies",
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                }
//
//                else -> Unit
//            }
//        }
    }
}

@Composable
fun ErrorRetryButton(onClick: () -> Unit) {
    Text(text = "Reload", fontSize = 20.sp, color = Color.Black, modifier = Modifier.clickable{ onClick() })
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesDbAppTheme {
//        Greeting("Android")
    }
}