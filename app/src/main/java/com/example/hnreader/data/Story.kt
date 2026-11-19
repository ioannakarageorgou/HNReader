package com.example.hnreader.data

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val id: Long,
    val title: String = "",
    val url: String? = null,
    val by: String = "",
    val score: Int = 0,
    val time: Long = 0,
    val descendants: Int = 0,
)