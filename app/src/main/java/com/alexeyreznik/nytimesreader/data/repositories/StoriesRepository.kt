package com.alexeyreznik.nytimesreader.data.repositories

import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.data.network.NYTimesService
import com.alexeyreznik.nytimesreader.data.network.StoriesListResponse
import io.reactivex.Single

/**
 * Created by alexeyreznik on 12/01/2018.
 */
class StoriesRepository(private val service: NYTimesService) {

    fun getStories(section: String): Single<List<Story>> =
            service.getStories(section)
                    .map { response ->
                        when (response.status) {
                            StoriesListResponse.STATUS_OK -> return@map response.results
                            else -> throw Exception(String.format("Error. Status: %s", response.status))
                        }
                    }
}