package com.myapps.thecatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.myapps.thecatapp.ui.screens.CatScreen
import com.myapps.thecatapp.ui.screens.DetailScreen
import com.myapps.thecatapp.ui.screens.FavouritesScreen
import com.myapps.thecatapp.ui.theme.TheCatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCatAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.HomeGraph
                    ) {
                        navigation<Route.HomeGraph>(
                            startDestination = Route.Home
                        ) {
                            composable<Route.Home> {
                                CatScreen(
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            composable<Route.Favourites> {
                                FavouritesScreen(
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            composable<Route.Detail> {
                                DetailScreen(
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}