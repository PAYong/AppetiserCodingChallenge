package com.appetiser.itunesapp.api

import com.appetiser.itunesapp.data.SearchResult
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interface using Retrofit to connect to iTunes.
 * Design Pattern - Facade using Retrofit
 * Advantage: Easier to use by just creating interface to provide API data to client classes
 */
interface ItunesService {

    @GET("/repositories")
    fun retrieveRepositories(): Call<SearchResult>

    @GET("/search?term=star&amp;country=au&amp;media=movie")
    fun searchRepositories(): Call<SearchResult>
}