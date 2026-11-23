package com.example.hnreader.testutil

import com.example.hnreader.data.HackerNewsApi
import com.example.hnreader.data.Story
import java.io.IOException

class FakeHackerNewsApi(
    private val stories: List<Story> = emptyList(),
) : HackerNewsApi {

    /** Set to true to make every call fail like a lost connection. */
    var shouldFail = false

    override suspend fun getTopStoryIds(): List<Long> {
        if (shouldFail) throw IOException("No internet")
        return stories.map { it.id }
    }

    override suspend fun getStory(id: Long): Story {
        if (shouldFail) throw IOException("No internet")
        return stories.first { it.id == id }
    }
}
