package com.myapps.thecatapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.myapps.thecatapp.ui.screens.CatScreen
import com.myapps.thecatapp.ui.screens.DetailScreen
import com.myapps.thecatapp.ui.screens.FavouritesScreen
import com.myapps.thecatapp.ui.theme.TheCatAppTheme
import com.myapps.thecatapp.ui.theme.White

@Composable
fun App() {
    TheCatAppTheme(
        dynamicColor = false
    ) {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Route.Home)
                            }
                            .padding(start = 32.dp)
                            .size(32.dp),
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Screen",
                        tint = White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Route.Favourites)
                            }
                            .padding(end = 32.dp)
                            .size(32.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourites Screen",
                        tint = White
                    )
                }
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
                        CatScreen(
                            modifier = Modifier.padding(innerPadding),
                            goToDetail = { navController.navigate(Route.Detail(imageId = it)) }
                        )
                    }
                    composable<Route.Favourites> {
                        FavouritesScreen(
                            modifier = Modifier.padding(innerPadding),
                            goToDetail = { navController.navigate(Route.Detail(imageId = it)) }
                        )
                    }
                    composable<Route.Detail> {
                        val args = it.toRoute<Route.Detail>()
                        DetailScreen(
                            modifier = Modifier.padding(innerPadding),
                            imageId = args.imageId
                        )
                    }
                }
            }
        }
    }
}