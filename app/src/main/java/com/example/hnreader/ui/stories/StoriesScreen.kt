package com.example.hnreader.ui.stories

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hnreader.data.FakeStories
import com.example.hnreader.data.Story
import com.example.hnreader.ui.theme.HNReaderTheme

@Composable
fun StoriesScreen(
    stories: List<Story>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(stories, key = { it.id }) { story ->
            StoryRow(story)
            HorizontalDivider()
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoriesScreenPreview() {
    HNReaderTheme {
        Surface {
            StoriesScreen(stories = FakeStories.stories)
        }
    }
}