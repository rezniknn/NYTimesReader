package com.alexeyreznik.nytimesreader.data.repositories

import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.data.network.NYTimesService
import com.alexeyreznik.nytimesreader.data.network.StoriesListResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by alexeyreznik on 15/01/2018.
 */
class StoriesRepositoryTest {

    @Mock
    private lateinit var service: NYTimesService

    private lateinit var storiesRepository: StoriesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        storiesRepository = StoriesRepository(service)
    }

    @Test
    fun test_getStories_Success() {
        val section = "section"
        val url = "url"
        val story = Story(url = url)
        val stories = listOf<Story>(story)
        `when`(service.getStories(section)).thenReturn(Single.just(StoriesListResponse(status = StoriesListResponse.STATUS_OK, results = stories)))


        val testObservable = storiesRepository.getStories(section).test()


        testObservable.assertNoErrors()
        testObservable.assertValue { receivedStories ->
            receivedStories == stories
        }
    }

    @Test
    fun test_getStories_Failure_NetworkError() {
        val section = "section"
        val url = "url"
        `when`(service.getStories(section)).thenReturn(Single.error(Exception()))


        val testObservable = storiesRepository.getStories(section).test()


        testObservable.assertError(Exception::class.java)
    }


    @Test
    fun test_getStories_Failure_ErrorStatus() {
        val section = "section"
        val url = "url"
        val status = "error"
        `when`(service.getStories(section)).thenReturn(Single.just(StoriesListResponse(status = status)))


        val testObservable = storiesRepository.getStories(section).test()


        testObservable.assertError(Exception::class.java)
    }
}