package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    goToDetail: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        CatsGrid(
//            modifier = Modifier.weight(1f),
//            catBreeds = catBreeds.filter { it.isFavourite },
//            addOrRemoveFromFavourits = sharedViewModel::addOrRemoveCatFromFavourites,
//            goToDetail = goToDetail
//        )
    }
}