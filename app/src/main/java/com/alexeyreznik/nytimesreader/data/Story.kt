package com.alexeyreznik.nytimesreader.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by alexeyreznik on 12/01/2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Story(@Json(name = "section") private val section: String,
                 @Json(name = "subsection") private val subsection: String,
                 @Json(name = "title") private val title: String,
                 @Json(name = "abstract") private val abstract: String,
                 @Json(name = "url") private val url: String,
                 @Json(name = "byline") private val byline: String,
                 @Json(name = "item_type") private val itemType: String,
                 @Json(name = "published_date") private val publishedDate: String,
                 @Json(name = "multimedia") private val multimedia: List<Multimedia>) : Parcelable {
    @Parcelize
    data class Multimedia(@Json(name = "url") private val url: String,
                          @Json(name = "format") private val format: String,
                          @Json(name = "type") private val type: String,
                          @Json(name = "subtype") private val subtype: String,
                          @Json(name = "caption") private val caption: String,
                          @Json(name = "copyright") private val copyright: String,
                          @Json(name = "height") private val height: Int,
                          @Json(name = "width") private val width: Int) : Parcelable
}