package com.alexeyreznik.nytimesreader.presentation.views

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.alexeyreznik.nytimesreader.R
import com.alexeyreznik.nytimesreader.data.Story
import org.junit.Rule
import org.junit.Test

/**
 * Created by alexeyreznik on 19/01/2018.
 */
class StoryDetailsActivityTest {

    @JvmField
    @Rule
    var activityRule: ActivityTestRule<StoryDetailsActivity> = ActivityTestRule(
            StoryDetailsActivity::class.java)


    @Test
    fun test_StoryDetailsDisplayed() {
        val expectedTitle = "title"
        val expectedAbstract = "abstract"
        val expectedByline = "byline"
        val expectedUrl = "url"
        val story = Story(title = expectedTitle, byline = expectedByline, abstract = expectedAbstract, url = expectedUrl)


        val intent = Intent()
        intent.putExtra(StoryDetailsActivity.ARG_STORY, story)
        activityRule.launchActivity(intent)


        onView(withId(R.id.story_title)).check(ViewAssertions.matches(ViewMatchers.withText(expectedTitle)))
        onView(withId(R.id.abstrct)).check(ViewAssertions.matches(ViewMatchers.withText(expectedAbstract)))
        onView(withId(R.id.byline)).check(ViewAssertions.matches(ViewMatchers.withText(expectedByline)))
        onView(withId(R.id.link)).check(ViewAssertions.matches(ViewMatchers.withText(expectedUrl)))
    }
}