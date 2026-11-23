package com.example.hnreader.data

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class StoriesRepository(
    private val api: HackerNewsApi = HackerNewsApi.create(),
) {
    suspend fun getTopStories(limit: Int = 30): List<Story> = coroutineScope {
        val ids = api.getTopStoryIds().take(limit)

        ids.map { id -> async { api.getStory(id) } }
            .awaitAll()
    }

    suspend fun getStory(id: Long): Story = api.getStory(id)
}