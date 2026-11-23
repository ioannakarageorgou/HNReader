package com.example.hnreader.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnreader.data.StoriesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoryDetailViewModel(
    private val storyId: Long,
    private val repository: StoriesRepository = StoriesRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<StoryDetailUiState>(StoryDetailUiState.Loading)
    val uiState: StateFlow<StoryDetailUiState> = _uiState.asStateFlow()

    init {
        loadStory()
    }

    fun loadStory() {
        viewModelScope.launch {
            _uiState.value = StoryDetailUiState.Loading
            _uiState.value = try {
                StoryDetailUiState.Success(repository.getStory(storyId))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("StoryDetailViewModel", "Failed to load story $storyId", e)
                StoryDetailUiState.Error("Couldn't load this story. Check your connection and try again.")
            }
        }
    }
}