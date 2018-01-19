package com.alexeyreznik.nytimesreader.presentation.views

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.alexeyreznik.nytimesreader.App
import com.alexeyreznik.nytimesreader.R
import com.alexeyreznik.nytimesreader.data.Sections
import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.data.repositories.StoriesRepository
import com.alexeyreznik.nytimesreader.di.components.DaggerFakeApplicationComponent
import com.alexeyreznik.nytimesreader.di.modules.FakeApplicationModule
import com.alexeyreznik.nytimesreader.RecyclerViewMatcher
import com.alexeyreznik.nytimesreader.hasChildren
import com.alexeyreznik.nytimesreader.nthChildOf
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


/**
 * Created by alexeyreznik on 19/01/2018.
 */
class StoriesListActivityTest {

    @JvmField
    @Rule
    var activityRule: ActivityTestRule<StoriesListActivity> = ActivityTestRule(
            StoriesListActivity::class.java)

    @Mock
    private lateinit var mockStoriesRepository: StoriesRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as App

        val testComponent = DaggerFakeApplicationComponent.builder()
                .fakeApplicationModule(FakeApplicationModule(mockStoriesRepository))
                .build()
        app.component = testComponent

        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_RecyclerViewShowingCorrectFirstItem() {
        val expectedTitle = "title"
        val expectedByline = "byline"
        val section = Sections.HOME.title
        `when`(mockStoriesRepository.getStories(section)).thenReturn(Single.just(listOf(Story(title = expectedTitle, byline = expectedByline))))


        activityRule.launchActivity(Intent())


        Espresso.onView(RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.title)).check(ViewAssertions.matches(ViewMatchers.withText(expectedTitle)))
        Espresso.onView(RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.byline)).check(ViewAssertions.matches(ViewMatchers.withText(expectedByline)))
    }


    @Test
    fun test_getStoriesErrorDisplayed() {
        val section = Sections.HOME.title
        `when`(mockStoriesRepository.getStories(section)).thenReturn(Single.error(Exception()))


        activityRule.launchActivity(Intent())


        onView(ViewMatchers.withText(activityRule.activity.getString(R.string.error_failed_to_get_stories)))
                .inRoot(withDecorView(Matchers.not(Matchers.`is`(activityRule.activity.window.decorView))))
                .check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun test_SectionsViewPopulated() {
        val section = Sections.HOME.title
        `when`(mockStoriesRepository.getStories(section)).thenReturn(Single.just(listOf()))


        activityRule.launchActivity(Intent())


        onView(withId(R.id.container)).check(matches(allOf(
                isDisplayed(),
                hasChildren(`is`(Sections.values().size)))))
    }

    @Test
    fun test_SectionSelectedOnClick() {
        val sectionHome = Sections.HOME.title
        val sectionOpinion = Sections.OPINION.title
        val expectedTitle = "title"
        val expectedByline = "byline"
        val story = Story(title = expectedTitle, byline = expectedByline)
        `when`(mockStoriesRepository.getStories(sectionHome)).thenReturn(Single.error(Exception()))
        `when`(mockStoriesRepository.getStories(sectionOpinion)).thenReturn(Single.just(listOf(story)))


        activityRule.launchActivity(Intent())
        onView(nthChildOf(withId(R.id.container), 1)).perform(ViewActions.click())


        Espresso.onView(RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.title)).check(ViewAssertions.matches(ViewMatchers.withText(expectedTitle)))
    }

    @Test
    fun test_storyDetailsOpenOnClick() {
        val expectedTitle = "title"
        val expectedByline = "byline"
        val section = Sections.HOME.title
        val story = Story(title = expectedTitle, byline = expectedByline)
        `when`(mockStoriesRepository.getStories(section)).thenReturn(Single.just(listOf(story)))


        activityRule.launchActivity(Intent())
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))


        intended(hasComponent(StoryDetailsActivity::class.java.name))
        intended(hasExtra(StoryDetailsActivity.ARG_STORY, story))
    }
}