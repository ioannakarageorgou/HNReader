package com.example.hnreader.ui.stories

import com.example.hnreader.data.Story
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class StoryFormattingTest {

    private val now = 1_700_000_000L

    private fun story(url: String? = null, time: Long = now) =
        Story(id = 1, title = "Title", url = url, time = time)

    @Test
    fun `timeAgo shows just now for under a minute`() {
        assertEquals("just now", story(time = now - 30).timeAgo(nowSeconds = now))
    }

    @Test
    fun `timeAgo shows minutes`() {
        assertEquals("5m ago", story(time = now - 5 * 60).timeAgo(nowSeconds = now))
    }

    @Test
    fun `timeAgo shows hours`() {
        assertEquals("2h ago", story(time = now - 2 * 60 * 60).timeAgo(nowSeconds = now))
    }

    @Test
    fun `timeAgo shows days`() {
        assertEquals("3d ago", story(time = now - 3 * 24 * 60 * 60).timeAgo(nowSeconds = now))
    }

    @Test
    fun `domain strips www`() {
        assertEquals("example.com", story(url = "https://www.example.com/post").domain)
    }

    @Test
    fun `domain keeps other subdomains`() {
        assertEquals("blog.example.com", story(url = "https://blog.example.com/post").domain)
    }

    @Test
    fun `domain is null when there is no url`() {
        assertNull(story(url = null).domain)
    }

    @Test
    fun `domain is null for a malformed url`() {
        assertNull(story(url = "not a url").domain)
    }
}