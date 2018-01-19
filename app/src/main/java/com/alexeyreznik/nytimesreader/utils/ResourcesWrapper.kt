package com.alexeyreznik.nytimesreader.utils

import android.content.Context

/**
 * Created by alexeyreznik on 19/01/2018.
 */
class ResourcesWrapper(private val context: Context) {

    fun getQuantityString(resourceId: Int, quantity: Int): String {
        return context.resources.getQuantityString(resourceId, quantity)
    }
}