package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapps.thecatapp.R
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (favouriteCatBreeds.isEmpty()) {
                Text(
                    modifier = Modifier.padding(top = 120.dp),
                    text = stringResource(R.string.empty_favourites),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            } else {
                CatsGrid(
                    modifier = Modifier.weight(1f),
                    catBreeds = favouriteCatBreeds,
                    goToDetail = goToDetail,
                    addOrRemoveFromFavourites = favouritesViewModel::removeCatFromFavourites,
                    lazyGridState = rememberLazyGridState(),
                    isFromFavourites = true
                )
            }
        }
    }
}