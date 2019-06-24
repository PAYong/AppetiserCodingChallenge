package com.appetiser.itunesapp.api

import com.appetiser.itunesapp.data.SearchResult
import retrofit2.Call
import retrofit2.http.GET

/**
 *  Interface using Retrofit to connect to iTunes.
 */
interface ItunesService {

    @GET("/repositories")
    fun retrieveRepositories(): Call<SearchResult>

    @GET("/search?term=star&amp;country=au&amp;media=movie")
    fun searchRepositories(): Call<SearchResult>
}