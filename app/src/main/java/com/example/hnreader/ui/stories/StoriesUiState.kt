package com.example.hnreader.ui.stories

import com.example.hnreader.data.Story

sealed interface StoriesUiState {
    data object Loading : StoriesUiState
    data class Success(val stories: List<Story>) : StoriesUiState
    data class Error(val message: String) : StoriesUiState
}