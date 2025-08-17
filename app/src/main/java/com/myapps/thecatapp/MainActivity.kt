package com.myapps.thecatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.myapps.thecatapp.ui.screens.CatScreen
import com.myapps.thecatapp.ui.screens.DetailScreen
import com.myapps.thecatapp.ui.screens.FavouritesScreen
import com.myapps.thecatapp.ui.theme.TheCatAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCatAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(Route.Home)
                                    }
                                    .padding(start = 32.dp)
                                    .size(32.dp),
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home Screen"
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
                                contentDescription = "Favourites Screen"
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
//                                val sharedViewModel = it.sharedViewModel<SharedViewModel>(navController)
                                CatScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    goToDetail = { navController.navigate(Route.Detail) }
                                )
                            }
                            composable<Route.Favourites> {
//                                val sharedViewModel = it.sharedViewModel<SharedViewModel>(navController)
                                FavouritesScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    goToDetail = { navController.navigate(Route.Detail) }
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

//@Composable
//private inline fun<reified T: ViewModel> NavBackStackEntry.sharedViewModel(
//    navController: NavController
//): T {
//    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
//    val parentEntry = remember(this) {
//        navController.getBackStackEntry(navGraphRoute)
//    }
//    return koinViewModel(viewModelStoreOwner = parentEntry)
//}