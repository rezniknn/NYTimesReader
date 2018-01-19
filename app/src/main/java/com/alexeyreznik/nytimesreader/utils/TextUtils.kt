package com.alexeyreznik.nytimesreader.utils

import com.alexeyreznik.nytimesreader.R
import org.joda.time.DateTime
import org.joda.time.Period
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexeyreznik on 12/01/2018.
 */
fun calculateStoryAge(resourcesWrapper: ResourcesWrapper, publishDateString: String, currentDate: Date): String {
    val publishDate: Date?
    try {
        publishDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(publishDateString)
    } catch (ex: ParseException) {
        return ""
    }

    val period = Period(DateTime(publishDate), DateTime(currentDate))
    return when (period.hours) {
        0 -> {
            var minutes = period.minutes
            if (minutes == 0) minutes++
            String.format("%d %s", minutes, resourcesWrapper.getQuantityString(R.plurals.minutes_ago, period.minutes))
        }
        else -> String.format("%d %s", period.hours, resourcesWrapper.getQuantityString(R.plurals.hours_ago, period.hours))
    }
}