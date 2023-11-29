package com.yassir.movies.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.yassir.movies.BuildConfig

fun String.toImagesUrl() = "${BuildConfig.BASE_IMG_URL}$this"

@Composable
fun appImageLoader(): ImageLoader {
    val context = LocalContext.current

    return ImageLoader.Builder(context)
        .crossfade(true)
        .respectCacheHeaders(false)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.05)
                .build()
        }
        .build()
}