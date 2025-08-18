package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myapps.thecatapp.ui.composables.CatsGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    goToDetail: (String) -> Unit,
) {
    val favouritesViewModel = koinViewModel<FavouritesViewModel>()
    val favouriteCatBreeds by favouritesViewModel.favouriteCatBreeds.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CatsGrid(
                modifier = Modifier.weight(1f),
                catBreeds = favouriteCatBreeds,
                goToDetail = goToDetail,
                addOrRemoveFromFavourites = favouritesViewModel::removeCatFromFavourites,
                showLifespan = true
            )
        }
    }
}