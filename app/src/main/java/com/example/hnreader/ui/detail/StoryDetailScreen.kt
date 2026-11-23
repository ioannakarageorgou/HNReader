package com.example.hnreader.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hnreader.R
import com.example.hnreader.data.FakeStories
import com.example.hnreader.data.Story
import com.example.hnreader.ui.common.ErrorState
import com.example.hnreader.ui.common.LoadingState
import com.example.hnreader.ui.stories.domain
import com.example.hnreader.ui.stories.timeAgo
import com.example.hnreader.ui.theme.HNReaderTheme

/** Stateful: creates the ViewModel for this story and observes it. */
@Composable
fun StoryDetailRoute(
    storyId: Long,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoryDetailViewModel = viewModel { StoryDetailViewModel(storyId) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    StoryDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::loadStory,
        onOpenLink = { url -> uriHandler.openUri(url) },
        modifier = modifier,
    )
}

/** Stateless: draws whatever state it's given. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryDetailScreen(
    uiState: StoryDetailUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onOpenLink: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Story") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        when (uiState) {
            StoryDetailUiState.Loading -> LoadingState(Modifier.padding(innerPadding))
            is StoryDetailUiState.Success -> StoryDetailContent(uiState.story, onOpenLink, Modifier.padding(innerPadding))
            is StoryDetailUiState.Error -> ErrorState(uiState.message, onRetry, Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun StoryDetailContent(
    story: Story,
    onOpenLink: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(story.title, style = MaterialTheme.typography.headlineSmall)

        story.domain?.let { domain ->
            Text(
                text = domain,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Text(
            text = "${story.score} points · by ${story.by} · ${story.timeAgo()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = "${story.descendants} comments",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(8.dp))

        val url = story.url
        if (url != null) {
            Button(onClick = { onOpenLink(url) }) {
                Text("Open link")
            }
        } else {
            Text(
                text = "This is a text post. Its text and comments come in a later step.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(name = "Link post", showBackground = true)
@Composable
private fun StoryDetailLinkPreview() {
    HNReaderTheme {
        StoryDetailScreen(
            uiState = StoryDetailUiState.Success(FakeStories.stories.first()),
            onBack = {},
            onRetry = {},
            onOpenLink = {},
        )
    }
}

@Preview(name = "Text post", showBackground = true)
@Composable
private fun StoryDetailTextPreview() {
    HNReaderTheme {
        StoryDetailScreen(
            uiState = StoryDetailUiState.Success(FakeStories.stories[1]),
            onBack = {},
            onRetry = {},
            onOpenLink = {},
        )
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
private fun StoryDetailErrorPreview() {
    HNReaderTheme {
        StoryDetailScreen(
            uiState = StoryDetailUiState.Error("Couldn't load this story."),
            onBack = {},
            onRetry = {},
            onOpenLink = {},
        )
    }
}
