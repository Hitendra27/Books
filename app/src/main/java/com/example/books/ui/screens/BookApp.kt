package com.example.books.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import com.example.books.R
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

sealed class Screen(
    val route: String,
    val label: String
) {
    object List : Screen("list", "Books")
    object Detail : Screen("details", "Details")
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BookApp() {
    val viewModel: BookViewModel = viewModel()
    val navController = rememberAnimatedNavController()
    val currentDestination by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (currentDestination?.destination?.route) {
                            Screen.List.route -> stringResource(R.string.app_name)
                            //Screen.Detail.route -> Screen.Detail.label
                            else -> ""
                        },
                        style = MaterialTheme.typography.displayMedium
                    )

        },
                navigationIcon = {
                    if (currentDestination?.destination?.route == Screen.Detail.route) {
                        IconButton(onClick = { navController.navigateUp()}) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
                }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.List.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500)) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500)) + fadeOut()
            },
            // enterTransition = { fadeIn(animationSpec = tween(500)) },
           // exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -300 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 300 }) + fadeOut() },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.List.route) {
                BookListScreen(
                    viewModel = viewModel,
                    onBookSelected = { selectedBook ->
                        viewModel.loadBookById(selectedBook)
                        navController.navigate(Screen.Detail.route)
                    }
                )
            }
            composable(Screen.Detail.route) {
                BookDetailScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Preview
@Composable
fun BookAppPreview() {}