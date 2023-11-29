package com.yassir.movies.data.datasources.network

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(private val ktor: HttpClient) : ApiService {

}