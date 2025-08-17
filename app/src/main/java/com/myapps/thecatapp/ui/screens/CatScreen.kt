package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myapps.thecatapp.ui.composables.BreedSearchBar
import com.myapps.thecatapp.ui.composables.CatsGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun CatScreen(
    modifier: Modifier = Modifier,
    goToDetail: () -> Unit
) {
    val catViewModel = koinViewModel<CatViewModel>()
    val catBreeds by catViewModel.catBreeds.collectAsState()

    var breedSearch by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        catViewModel.loadCatsWithFavourites()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BreedSearchBar(
            modifier = Modifier.padding(bottom = 32.dp),
            breed = breedSearch,
            onChangeBreed = { breedSearch = it }
        )

        CatsGrid(
            modifier = Modifier.weight(1f),
            catBreeds = catBreeds,
            addOrRemoveFromFavourits = catViewModel::addOrRemoveCatFromFavourites,
            loadNextPage = catViewModel::loadNextPage,
            goToDetail = goToDetail
        )
    }
}