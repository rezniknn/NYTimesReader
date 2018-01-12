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
data class Story(@Json(name = "section") val section: String,
                 @Json(name = "subsection") val subsection: String,
                 @Json(name = "title") val title: String,
                 @Json(name = "abstract") val abstract: String,
                 @Json(name = "url") val url: String,
                 @Json(name = "byline") val byline: String,
                 @Json(name = "item_type") val itemType: String,
                 @Json(name = "published_date") val publishedDate: String,
                 @Json(name = "multimedia") val multimedia: List<Multimedia>) : Parcelable {
    @Parcelize
    data class Multimedia(@Json(name = "url") val url: String,
                          @Json(name = "format") val format: String,
                          @Json(name = "type") val type: String,
                          @Json(name = "subtype") val subtype: String,
                          @Json(name = "caption") val caption: String,
                          @Json(name = "copyright") val copyright: String,
                          @Json(name = "height") val height: Int,
                          @Json(name = "width") val width: Int) : Parcelable
}