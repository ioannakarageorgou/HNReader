package com.example.hnreader.ui.detail

import com.example.hnreader.data.StoriesRepository
import com.example.hnreader.data.Story
import com.example.hnreader.testutil.FakeHackerNewsApi
import com.example.hnreader.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StoryDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val stories = listOf(
        Story(id = 1, title = "First"),
        Story(id = 2, title = "Second"),
        Story(id = 3, title = "Third"),
    )

    private fun viewModel(storyId: Long, api: FakeHackerNewsApi) =
        StoryDetailViewModel(storyId = storyId, repository = StoriesRepository(api = api))

    @Test
    fun `starts in loading`() = runTest {
        val viewModel = viewModel(storyId = 2, api = FakeHackerNewsApi(stories))

        assertEquals(StoryDetailUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `shows the story with the requested id`() = runTest {
        val viewModel = viewModel(storyId = 2, api = FakeHackerNewsApi(stories))

        advanceUntilIdle()

        assertEquals(StoryDetailUiState.Success(stories[1]), viewModel.uiState.value)
    }

    @Test
    fun `shows error when loading fails`() = runTest {
        val api = FakeHackerNewsApi(stories).apply { shouldFail = true }
        val viewModel = viewModel(storyId = 2, api = api)

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is StoryDetailUiState.Error)
    }

    @Test
    fun `retry after an error shows the story`() = runTest {
        val api = FakeHackerNewsApi(stories).apply { shouldFail = true }
        val viewModel = viewModel(storyId = 2, api = api)
        advanceUntilIdle()

        api.shouldFail = false
        viewModel.loadStory()
        advanceUntilIdle()

        assertEquals(StoryDetailUiState.Success(stories[1]), viewModel.uiState.value)
    }
}
