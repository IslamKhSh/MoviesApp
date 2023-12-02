package com.yassir.movies.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.yassir.movies.data.errorHandling.PagingError
import com.yassir.movies.entities.Movie
import com.yassir.movies.ui.components.ErrorMessage
import com.yassir.movies.ui.components.LoadingNextPageItem
import com.yassir.movies.ui.components.PageLoader
import com.yassir.movies.utils.appImageLoader
import com.yassir.movies.utils.toImagesUrl

@Composable
fun MoviesListScreen(viewModel: MoviesListViewModel = hiltViewModel()) {
    val moviePagingItems = viewModel.uiState.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(moviePagingItems.itemCount) { index ->
            MovieItem(moviePagingItems[index]!!)
        }

        moviePagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillMaxWidth()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = moviePagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier.height(36.dp)) }
                }

                loadState.append is LoadState.Error -> {
                    val error = moviePagingItems.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier,
                            message = (error.error as PagingError).errorMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(bottom = 8.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
            .padding(6.dp)
            .clickable {
            }
    ) {
        SubcomposeAsyncImage(
            model = movie.image.toImagesUrl(),
            contentDescription = null,
            imageLoader = appImageLoader(),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
        )

        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .fillMaxSize()
        ) {
            Text(text = movie.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = movie.releaseYear,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
