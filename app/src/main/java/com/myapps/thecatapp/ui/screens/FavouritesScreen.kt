package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
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
    goToDetail: () -> Unit,
) {
    val favouritesViewModel = koinViewModel<FavouritesViewModel>()
    val favouriteCats by favouritesViewModel.favouriteCats.collectAsState()
    val isLoading by favouritesViewModel.isLoading.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CatsGrid(
            modifier = Modifier.weight(1f),
            catBreeds = favouriteCats.filter { it.isFavourite },
            goToDetail = goToDetail,
            showLifespan = true
        )
    }
}