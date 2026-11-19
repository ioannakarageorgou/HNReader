package com.example.hnreader.ui.stories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnreader.data.StoriesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoriesViewModel(
    private val repository: StoriesRepository = StoriesRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<StoriesUiState>(StoriesUiState.Loading)
    val uiState: StateFlow<StoriesUiState> = _uiState.asStateFlow()

    init {
        loadStories()
    }

    fun loadStories() {
        viewModelScope.launch {
            _uiState.value = StoriesUiState.Loading
            _uiState.value = try {
                StoriesUiState.Success(repository.getTopStories())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("StoriesViewModel", "Failed to load stories", e)
                StoriesUiState.Error("Couldn't load stories. Check your connection and try again.")
            }
        }
    }
}