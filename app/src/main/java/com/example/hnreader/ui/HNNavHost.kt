package com.example.hnreader.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hnreader.ui.stories.StoriesRoute
import kotlinx.serialization.Serializable

@Serializable
data object StoriesDestination

@Composable
fun HNNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = StoriesDestination,
        modifier = modifier,
    ) {
        composable<StoriesDestination> {
            StoriesRoute()
        }
    }
}