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
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.books.R

sealed class Screen(
    val route: String,
    val label: String
) {
    object List : Screen("list", "Books")
    object Detail : Screen("details", "Details")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookApp() {
    val viewModel: BookViewModel = viewModel()
    val navController = rememberNavController()
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
        NavHost(
            navController = navController,
            startDestination = Screen.List.route,
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