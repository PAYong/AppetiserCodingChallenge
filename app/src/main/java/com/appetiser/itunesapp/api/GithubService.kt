package com.appetiser.itunesapp.api

import com.appetiser.itunesapp.data.SearchResult
import retrofit2.Call
import retrofit2.http.GET

interface ItunesService {

    @GET("/repositories")
    fun retrieveRepositories(): Call<SearchResult>

    @GET("/search?term=star&amp;country=au&amp;media=movie") //sample search // https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie
    fun searchRepositories(): Call<SearchResult>
}