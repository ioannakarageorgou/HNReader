package com.example.hnreader.ui.stories

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hnreader.data.FakeStories
import com.example.hnreader.data.Story
import com.example.hnreader.ui.common.ErrorState
import com.example.hnreader.ui.common.LoadingState
import com.example.hnreader.ui.theme.HNReaderTheme

@Composable
fun StoriesRoute(
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoriesViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StoriesScreen(
        uiState = uiState,
        onRetry = viewModel::loadStories,
        onStoryClick = onStoryClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoriesScreen(
    uiState: StoriesUiState,
    onRetry: () -> Unit,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("Top Stories") }) },
    ) { innerPadding ->
        when (uiState) {
            StoriesUiState.Loading -> LoadingState(Modifier.padding(innerPadding))
            is StoriesUiState.Success -> StoriesList(uiState.stories, onStoryClick, contentPadding = innerPadding)
            is StoriesUiState.Error -> ErrorState(uiState.message, onRetry, Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun StoriesList(
    stories: List<Story>,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(modifier = modifier, contentPadding = contentPadding) {
        items(stories, key = { it.id }) { story ->
            StoryRow(story = story, onClick = { onStoryClick(story) })
            HorizontalDivider()
        }
    }
}

// Previews: one per state. No ViewModel needed, which is the point of the stateless screen.

@Preview(name = "Success", showBackground = true)
@Preview(name = "Success dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoriesSuccessPreview() {
    HNReaderTheme {
        Surface {
            StoriesScreen(
                uiState = StoriesUiState.Success(FakeStories.stories),
                onRetry = {},
                onStoryClick = {},
            )
        }
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
private fun StoriesLoadingPreview() {
    HNReaderTheme {
        Surface {
            StoriesScreen(uiState = StoriesUiState.Loading, onRetry = {}, onStoryClick = {})
        }
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
private fun StoriesErrorPreview() {
    HNReaderTheme {
        Surface {
            StoriesScreen(
                uiState = StoriesUiState.Error("Couldn't load stories. Check your connection."),
                onRetry = {},
                onStoryClick = {},
            )
        }
    }
}