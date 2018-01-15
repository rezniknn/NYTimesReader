package com.alexeyreznik.nytimesreader.presentation.presenters

import android.view.View
import com.alexeyreznik.nytimesreader.data.Sections
import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.domain.GetStoriesListUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

/**
 * Created by alexeyreznik on 15/01/2018.
 */
class StoriesListPresenterTest {

    @Mock
    private lateinit var getStoriesUseCase: GetStoriesListUseCase

    @Mock
    private lateinit var view: StoriesListPresenter.StoriesListView

    private lateinit var storiesListPresenter: StoriesListPresenter

    private val immediateScheduler = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setInitIoSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }

        storiesListPresenter = StoriesListPresenter(getStoriesUseCase)
        storiesListPresenter.attachView(view)
    }

    @Test
    fun test_getStoriesList_Success_DefaultSection() {
        val url = "url"
        val story = Story(url = url)
        val stories = listOf(story)
        `when`(getStoriesUseCase.execute(Sections.HOME.title)).thenReturn(Single.just(stories))


        storiesListPresenter.getStoriesList()


        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).showStoriesList(stories)
        verify(view, never()).showError()
    }

    @Test
    fun test_getStoriesList_Success_SelectSection() {
        val url = "url"
        val story = Story(url = url)
        val stories = listOf(story)
        `when`(getStoriesUseCase.execute(Sections.ARTS.title)).thenReturn(Single.just(stories))


        storiesListPresenter.section = Sections.ARTS.title


        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).showStoriesList(stories)
        verify(view, never()).showError()
    }

    @Test
    fun test_getStoriesList_Failure() {
        `when`(getStoriesUseCase.execute(Sections.HOME.title)).thenReturn(Single.error(Exception()))


        storiesListPresenter.getStoriesList()


        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).showError()
        verify(view, never()).showStoriesList(com.alexeyreznik.nytimesreader.utils.any())
    }


    @Test
    fun test_StoryClick() {
        val url = "url"
        val story = Story(url = url)
        val onClickSubject = PublishSubject.create<Pair<Story, View?>>()
        storiesListPresenter.registerStoryOnClickSubject(onClickSubject)


        onClickSubject.onNext(Pair(story, null))


        verify(view).navigateToStoryDetails(story, null)
    }


    @Test
    fun test_SectionClick() {
        val section = "section"
        val onClickSubject = PublishSubject.create<String>()
        storiesListPresenter.registerSectionOnClickSubject(onClickSubject)
        `when`(getStoriesUseCase.execute(section)).thenReturn(Single.just(listOf()))


        onClickSubject.onNext(section)


        verify(getStoriesUseCase).execute(section)
    }
}