package com.example.hnreader.ui.stories

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
class StoriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val stories = listOf(
        Story(id = 1, title = "First"),
        Story(id = 2, title = "Second"),
        Story(id = 3, title = "Third"),
    )

    private fun viewModel(api: FakeHackerNewsApi) =
        StoriesViewModel(repository = StoriesRepository(api = api))

    @Test
    fun `starts in loading`() = runTest {
        val viewModel = viewModel(FakeHackerNewsApi(stories))

        assertEquals(StoriesUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `shows stories in order when loading succeeds`() = runTest {
        val viewModel = viewModel(FakeHackerNewsApi(stories))

        advanceUntilIdle()

        assertEquals(StoriesUiState.Success(stories), viewModel.uiState.value)
    }

    @Test
    fun `shows error when loading fails`() = runTest {
        val api = FakeHackerNewsApi(stories).apply { shouldFail = true }
        val viewModel = viewModel(api)

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is StoriesUiState.Error)
    }

    @Test
    fun `retry after an error shows stories`() = runTest {
        val api = FakeHackerNewsApi(stories).apply { shouldFail = true }
        val viewModel = viewModel(api)
        advanceUntilIdle()

        api.shouldFail = false
        viewModel.loadStories()
        advanceUntilIdle()

        assertEquals(StoriesUiState.Success(stories), viewModel.uiState.value)
    }
}
