package com.example.hnreader.data

object FakeStories {

    private val now = System.currentTimeMillis() / 1000
    private const val MINUTE = 60L
    private const val HOUR = 60 * MINUTE

    val stories = listOf(
        Story(
            id = 1,
            title = "Show HN: I built a tiny static site generator in 300 lines of Go",
            url = "https://github.com/someone/tinygen",
            by = "jdoe",
            score = 412,
            time = now - 2 * HOUR,
            descendants = 128,
        ),
        Story(
            id = 2,
            title = "Ask HN: How do you keep up with Android development?",
            url = null,
            by = "mobiledev",
            score = 87,
            time = now - 45 * MINUTE,
            descendants = 64,
        ),
        Story(
            id = 3,
            title = "Why SQLite is a good fit for mobile apps",
            url = "https://www.example.com/blog/sqlite-on-mobile",
            by = "dbfan",
            score = 1024,
            time = now - 7 * HOUR,
            descendants = 312,
        ),
        Story(
            id = 4,
            title = "Kotlin coroutines explained with diagrams",
            url = "https://medium.example.com/kotlin-coroutines",
            by = "kt_learner",
            score = 9,
            time = now - 5 * MINUTE,
            descendants = 0,
        ),
    )
}