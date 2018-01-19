package com.alexeyreznik.nytimesreader.utils

import com.alexeyreznik.nytimesreader.R
import junit.framework.Assert.assertEquals
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.intThat
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexeyreznik on 19/01/2018.
 */
class TextUtilsTest {

    @Mock
    private lateinit var resourcesWrapper: ResourcesWrapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_calculateStoryAge() {
        val currentDate = Date()
        `when`(resourcesWrapper.getQuantityString(ArgumentMatchers.eq(R.plurals.minutes_ago), intThat { value -> value == 1 || value == 0})).thenReturn("minute ago")
        `when`(resourcesWrapper.getQuantityString(ArgumentMatchers.eq(R.plurals.minutes_ago), intThat { value -> value > 1 })).thenReturn("minutes ago")
        `when`(resourcesWrapper.getQuantityString(ArgumentMatchers.eq(R.plurals.hours_ago), intThat { value -> value == 1 || value == 0})).thenReturn("hour ago")
        `when`(resourcesWrapper.getQuantityString(ArgumentMatchers.eq(R.plurals.hours_ago), intThat { value -> value > 1 })).thenReturn("hours ago")

        var publishedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(DateTime(currentDate).minusSeconds(30).toDate())
        var expectedDate = calculateStoryAge(resourcesWrapper, publishedDate, currentDate)
        assertEquals("1 minute ago", expectedDate)

        publishedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(DateTime(currentDate).minusMinutes(1).toDate())
        expectedDate = calculateStoryAge(resourcesWrapper, publishedDate, currentDate)
        assertEquals("1 minute ago", expectedDate)

        publishedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(DateTime(currentDate).minusMinutes(30).toDate())
        expectedDate = calculateStoryAge(resourcesWrapper, publishedDate, currentDate)
        assertEquals("30 minutes ago", expectedDate)

        publishedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(DateTime(currentDate).minusMinutes(60).toDate())
        expectedDate = calculateStoryAge(resourcesWrapper, publishedDate, currentDate)
        assertEquals("1 hour ago", expectedDate)

        publishedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(DateTime(currentDate).minusMinutes(90).toDate())
        expectedDate = calculateStoryAge(resourcesWrapper, publishedDate, currentDate)
        assertEquals("1 hour ago", expectedDate)

        publishedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(DateTime(currentDate).minusMinutes(120).toDate())
        expectedDate = calculateStoryAge(resourcesWrapper, publishedDate, currentDate)
        assertEquals("2 hours ago", expectedDate)
    }
}