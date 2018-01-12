package com.alexeyreznik.nytimesreader.domain

import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.data.repositories.StoriesRepository
import io.reactivex.Single

/**
 * Created by alexeyreznik on 12/01/2018.
 */
class GetStoriesListUseCase(private val storiesRepository: StoriesRepository) {

    fun execute(section: String): Single<List<Story>>
            = storiesRepository.getStories(section)
}