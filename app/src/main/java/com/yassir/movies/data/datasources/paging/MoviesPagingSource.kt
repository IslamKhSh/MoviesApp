package com.yassir.movies.data.datasources.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yassir.movies.data.datasources.network.ApiService
import com.yassir.movies.data.errorHandling.PagingErrorHandler
import com.yassir.movies.data.models.RemoteMovies
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesPagingSource @Inject constructor(
    private val apiService: ApiService,
) : PagingSource<Int, RemoteMovies.RemoteMovie>() {
    override fun getRefreshKey(state: PagingState<Int, RemoteMovies.RemoteMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteMovies.RemoteMovie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = apiService.getMoviesList(currentPage)
            LoadResult.Page(
                data = movies.results,
                prevKey = null,
                nextKey = if (movies.results.isEmpty()) null else movies.page + 1
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(PagingErrorHandler.catch(throwable))
        }
    }
}
