package com.alexeyreznik.nytimesreader.di.modules

import android.app.Application
import android.content.Context
import com.alexeyreznik.nytimesreader.R
import com.alexeyreznik.nytimesreader.data.repositories.StoriesRepository
import com.alexeyreznik.nytimesreader.data.network.NYTimesService
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by alexeyreznik on 12/01/2018.
 */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context
            = application

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val url = chain.request().url()
            val request = chain.request().newBuilder().url(String.format("%s?api-key=%s", url, context.getString(R.string.api_key))).build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideService(context: Context, okHttpClient: OkHttpClient): NYTimesService {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        return retrofit.create(NYTimesService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoriesRepository(service: NYTimesService): StoriesRepository
            = StoriesRepository(service)
}