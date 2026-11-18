package com.example.hnreader.ui.stories

import com.example.hnreader.data.Story
import java.net.URI

/** "https://www.example.com/post" -> "example.com". Null for posts without a link. */
val Story.domain: String?
    get() = url
        ?.let { runCatching { URI(it).host }.getOrNull() }
        ?.removePrefix("www.")

/** Unix seconds -> "just now", "5m ago", "2h ago", "3d ago". */
fun Story.timeAgo(nowSeconds: Long = System.currentTimeMillis() / 1000): String {
    val seconds = nowSeconds - time
    return when {
        seconds < 60 -> "just now"
        seconds < 60 * 60 -> "${seconds / 60}m ago"
        seconds < 24 * 60 * 60 -> "${seconds / (60 * 60)}h ago"
        else -> "${seconds / (24 * 60 * 60)}d ago"
    }
}