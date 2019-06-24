package com.appetiser.itunesapp.api

import com.appetiser.itunesapp.data.SearchResult
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Specifies the base URL
 * Creates a Retrofit object
 * Specifies GsonConverterFactory as the converter for its JSON deserialization.
 * Generates an implementation of the ItunesService interface using the Retrofit object
 */
class RepositoryRetriever {
    private val service: ItunesService

    companion object {
        const val BASE_URL =  "https://itunes.apple.com/"
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(ItunesService::class.java)
    }

    fun getRepositories(callback: Callback<SearchResult>) {
        val call = service.searchRepositories()
        call.enqueue(callback)
    }
}