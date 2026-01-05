package com.example.moviesdbapp.view.commonViews

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesdbapp.R
import com.example.moviesdbapp.data.remote.Movie
import com.example.moviesdbapp.data.remote.MoviesApi
import com.example.moviesdbapp.utils.AppConstants
import com.example.moviesdbapp.utils.AppScreens
import com.example.moviesdbapp.view.MoviesViewModel

@Composable
fun MovieItemUI(
    movie: Movie?,
    navHostController: NavHostController,
    moviesViewModel: MoviesViewModel,
    onItemClick: (Movie) -> Unit
) {

    val savedIconState = remember { mutableStateOf(Icons.Rounded.StarBorder) }
    val context = LocalContext.current

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MoviesApi.IMAGE_BASE_URL + "${movie?.poster_path ?: ""}")
            .size(Size.ORIGINAL).build()
    ).state
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navHostController.navigate(AppScreens.DetailsScreen.route + "/${movie?.id}")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {

            if (imageState is AsyncImagePainter.State.Error) {
                Image(
                    painter = painterResource(R.drawable.movies_placeholder),
                    contentScale = ContentScale.Fit,
                    contentDescription = "movies placeholder",
                    modifier = Modifier
                        .width(120.dp)
                        .height(160.dp)
                )
            } else {
                // Poster Image
                AsyncImage(
                    model = MoviesApi.IMAGE_BASE_URL + "${movie?.poster_path ?: ""}",
                    contentDescription = movie?.title ?: "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text Section
            Column(
                modifier = Modifier.fillMaxWidth()
            )
            {

                Text(
                    text = movie?.title ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie?.release_date ?: "",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie?.overview ?: "",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    savedIconState.value =
                        if (movie?.isSaved == true) Icons.Rounded.Star else Icons.Rounded.StarBorder
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        imageVector = savedIconState.value, contentDescription = "",
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .padding(top = 4.dp, start = 2.dp, bottom = 2.dp, end = 2.dp)
                            .clickable {
                                if (movie != null) {
                                    onItemClick(movie)
                                    movie.isSaved = !(movie.isSaved)
                                    if (movie.isSaved) {
                                        savedIconState.value = Icons.Rounded.Star
                                    } else {
                                        savedIconState.value = Icons.Rounded.StarBorder
                                    }
                                    moviesViewModel.saveUnsaveMovie(movie)
                                }
                            }, contentScale = ContentScale.Fit
                    )
                    Image(
                        imageVector = Icons.Default.Share,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .padding(top = 4.dp, start = 2.dp, bottom = 2.dp, end = 2.dp)
                            .clickable {
                                if (movie != null) {
                                    val sendIntent:Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, AppConstants.MOVIE_SHARING_DEEPLINK+(movie.id))
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, "Share this link via:")
                                    context.startActivity(shareIntent)
                                }
                            },
                        contentDescription = "Share button"
                    )
                }
            }
        }
    }
}
