package com.alexeyreznik.nytimesreader.data.rest

import com.alexeyreznik.nytimesreader.data.Story
import com.squareup.moshi.Json

/**
 * Created by alexeyreznik on 12/01/2018.
 */
data class StoriesListResponse(@Json(name = "status") val status: String,
                               @Json(name = "copyright") val copyright: String,
                               @Json(name = "section") val section: String,
                               @Json(name = "last_updated") val lastUpdated: String,
                               @Json(name = "num_results") val numResults: Int,
                               @Json(name = "results") val results: List<Story>)