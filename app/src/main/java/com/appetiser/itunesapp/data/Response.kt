package com.appetiser.itunesapp.data

import java.util.*

data class SearchResult(
        val resultCount: Int,
        val results: ArrayList<SearchItems>
)

data class SearchItems(
        val trackName: String?,
        val artworkUrl100: String?,
        val trackPrice: String?,
        val primaryGenreName: String?,

        //Other info
        val kind: String?,
        val longDescription: String?,
        val artistName: String?,
        val collectionName: String?,
        val collectionPrice: String?,
        val releaseDate: String?,
        val country: String?,
        val currency: String?
)


