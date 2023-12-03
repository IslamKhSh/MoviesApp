package com.yassir.movies.ui.screens.movieDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.yassir.movies.entities.MovieDetails
import com.yassir.movies.ui.components.ErrorView
import com.yassir.movies.ui.components.LoadingFullScreen
import com.yassir.movies.ui.screens.base.BaseUiState
import com.yassir.movies.utils.appImageLoader
import com.yassir.movies.utils.toImagesUrl

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        BaseUiState.Loading -> LoadingFullScreen()
        is BaseUiState.Error -> ErrorView(errorType = state.error, msg = state.errorMsg)
        is BaseUiState.Success -> MovieDetailsContent(state.data)
    }
}

@Composable
fun MovieDetailsContent(movieDetails: MovieDetails) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SubcomposeAsyncImage(
            model = movieDetails.image.toImagesUrl(),
            contentDescription = null,
            imageLoader = appImageLoader(),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = movieDetails.title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(12.dp)
        )

        Text(
            text = movieDetails.releaseYear,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Text(
            text = movieDetails.description,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
