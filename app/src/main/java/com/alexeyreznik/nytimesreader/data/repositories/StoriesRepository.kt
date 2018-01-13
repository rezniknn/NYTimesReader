package com.alexeyreznik.nytimesreader.data.repositories

import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.data.rest.NYTimesService
import com.alexeyreznik.nytimesreader.data.rest.StoriesListResponse
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
                            else -> throw Exception()
                        }
                    }
}