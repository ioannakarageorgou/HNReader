package com.example.hnreader.data

data class Story(
    val id: Long,
    val title: String,
    val url: String?,
    val by: String,
    val score: Int,
    val time: Long,
    val descendants: Int,
)