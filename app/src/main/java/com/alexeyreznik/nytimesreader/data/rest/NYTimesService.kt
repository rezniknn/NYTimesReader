package com.alexeyreznik.nytimesreader.data.rest

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by alexeyreznik on 12/01/2018.
 */
interface NYTimesService {

    @GET("topstories/v2/{section}.json")
    fun getStories(@Path("section") section: String): Single<StoriesListResponse>
}