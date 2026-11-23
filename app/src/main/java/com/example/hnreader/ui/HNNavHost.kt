package com.example.hnreader.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.hnreader.ui.detail.StoryDetailRoute
import com.example.hnreader.ui.stories.StoriesRoute
import kotlinx.serialization.Serializable

@Serializable
data object StoriesDestination

@Serializable
data class StoryDetailDestination(val storyId: Long)

@Composable
fun HNNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = StoriesDestination,
        modifier = modifier,
    ) {
        composable<StoriesDestination> {
            StoriesRoute(
                onStoryClick = { story ->
                    navController.navigate(StoryDetailDestination(storyId = story.id))
                },
            )
        }

        composable<StoryDetailDestination> { backStackEntry ->
            val destination: StoryDetailDestination = backStackEntry.toRoute()
            StoryDetailRoute(
                storyId = destination.storyId,
                onBack = { navController.navigateUp() },
            )
        }
    }
}