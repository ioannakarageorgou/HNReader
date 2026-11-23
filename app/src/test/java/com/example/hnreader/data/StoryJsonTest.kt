package com.example.hnreader.data

import kotlinx.serialization.SerializationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

/** Decodes real-shaped Hacker News JSON with the same Json config the app uses. */
class StoryJsonTest {

    private val json = HackerNewsApi.json

    @Test
    fun `decodes a link story and ignores unknown keys`() {
        val body = """
            {
              "by": "dhouston",
              "descendants": 71,
              "id": 8863,
              "kids": [9224, 8917, 8952],
              "score": 104,
              "time": 1175714200,
              "title": "My YC app: Dropbox - Throw away your USB drive",
              "type": "story",
              "url": "http://www.getdropbox.com/u/2/screencast.html"
            }
        """.trimIndent()

        val story = json.decodeFromString<Story>(body)

        assertEquals(
            Story(
                id = 8863,
                title = "My YC app: Dropbox - Throw away your USB drive",
                url = "http://www.getdropbox.com/u/2/screencast.html",
                by = "dhouston",
                score = 104,
                time = 1175714200,
                descendants = 71,
            ),
            story,
        )
    }

    @Test
    fun `missing url decodes to null for Ask HN posts`() {
        val body = """
            {
              "by": "tel",
              "descendants": 16,
              "id": 121003,
              "score": 25,
              "text": "What do you think?",
              "time": 1203647620,
              "title": "Ask HN: The Arc Effect",
              "type": "story"
            }
        """.trimIndent()

        val story = json.decodeFromString<Story>(body)

        assertNull(story.url)
    }

    @Test
    fun `missing descendants decodes to zero for job posts`() {
        val body = """
            {
              "by": "justin",
              "id": 192327,
              "score": 6,
              "time": 1210981217,
              "title": "Justin.tv is looking for a Lead Flash Engineer!",
              "type": "job",
              "url": ""
            }
        """.trimIndent()

        val story = json.decodeFromString<Story>(body)

        assertEquals(0, story.descendants)
    }

    @Test
    fun `missing id fails to decode`() {
        val body = """{ "title": "No id here" }"""

        assertThrows(SerializationException::class.java) {
            json.decodeFromString<Story>(body)
        }
    }

    @Test
    fun `decodes the top stories id list`() {
        val ids = json.decodeFromString<List<Long>>("[43521, 43519, 43500]")

        assertEquals(listOf(43521L, 43519L, 43500L), ids)
    }
}
