package com.example.hnreader.ui.detail

import com.example.hnreader.data.Story

sealed interface StoryDetailUiState {
    data object Loading : StoryDetailUiState
    data class Success(val story: Story) : StoryDetailUiState
    data class Error(val message: String) : StoryDetailUiState
}