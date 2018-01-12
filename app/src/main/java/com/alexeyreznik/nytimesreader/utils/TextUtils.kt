package com.alexeyreznik.nytimesreader.utils

import android.content.Context
import com.alexeyreznik.nytimesreader.R
import org.joda.time.DateTime
import org.joda.time.Period
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexeyreznik on 12/01/2018.
 */
fun calculateStoryAge(context: Context, publishDateString: String): String {
    val publishDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(publishDateString)
    val period = Period(DateTime(publishDate), DateTime())

    return when (period.hours) {
        0 -> String.format("%d %s", period.minutes, context.getString(R.string.minutes_ago))
        else -> String.format("%d %s", period.hours, context.getString(R.string.hours_ago))
    }
}