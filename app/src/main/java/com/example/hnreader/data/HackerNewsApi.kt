package com.example.hnreader.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApi {

    @GET("v0/topstories.json")
    suspend fun getTopStoryIds(): List<Long>

    @GET("v0/item/{id}.json")
    suspend fun getStory(@Path("id") id: Long): Story

    companion object {
        private const val BASE_URL = "https://hacker-news.firebaseio.com/"

        private val json = Json { ignoreUnknownKeys = true }

        fun create(): HackerNewsApi =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(HackerNewsApi::class.java)
    }
}