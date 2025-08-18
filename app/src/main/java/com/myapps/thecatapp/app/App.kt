package com.myapps.thecatapp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.myapps.thecatapp.app.composables.CatAppBar
import com.myapps.thecatapp.app.theme.TheCatAppTheme
import com.myapps.thecatapp.ui.screens.CatScreen
import com.myapps.thecatapp.ui.screens.DetailScreen
import com.myapps.thecatapp.ui.screens.FavouritesScreen

@Composable
fun App() {
    TheCatAppTheme(
        dynamicColor = false
    ) {
        val navController = rememberNavController()
        var selectedPage: Route by remember { mutableStateOf(Route.Home) }
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                CatAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    navController = navController,
                    selectedPage = selectedPage
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.HomeGraph
            ) {
                navigation<Route.HomeGraph>(
                    startDestination = Route.Home
                ) {
                    composable<Route.Home> {
                        selectedPage = Route.Home
                        CatScreen(
                            modifier = Modifier.padding(innerPadding),
                            goToDetail = { navController.navigate(Route.Detail(imageId = it)) }
                        )
                    }
                    composable<Route.Favourites> {
                        selectedPage = Route.Favourites
                        FavouritesScreen(
                            modifier = Modifier.padding(innerPadding),
                            goToDetail = { navController.navigate(Route.Detail(imageId = it)) }
                        )
                    }
                    composable<Route.Detail> {
                        val args = it.toRoute<Route.Detail>()
                        DetailScreen(
                            modifier = Modifier.padding(innerPadding),
                            imageId = args.imageId,
                            goBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}