package com.example.hnreader.ui.stories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hnreader.data.FakeStories
import com.example.hnreader.data.Story
import com.example.hnreader.ui.theme.HNReaderTheme

@Composable
fun StoryRow(story: Story, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = story.title,
            style = MaterialTheme.typography.titleMedium,
        )

        story.domain?.let { domain ->
            Text(
                text = domain,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = "${story.score} points · ${story.by} · ${story.timeAgo()} · ${story.descendants} comments",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryRowPreview() {
    HNReaderTheme {
        StoryRow(story = FakeStories.stories.first())
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryRowNoUrlPreview() {
    HNReaderTheme {
        StoryRow(story = FakeStories.stories[1])
    }
}