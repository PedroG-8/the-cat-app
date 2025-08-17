package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myapps.thecatapp.ui.composables.BreedSearchBar
import com.myapps.thecatapp.ui.composables.CatsGrid
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CatScreen(
    modifier: Modifier = Modifier,
    goToDetail: (String) -> Unit
) {
    val catViewModel = koinViewModel<CatViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val catBreeds by catViewModel.catBreeds.collectAsState()
    val searchedBreeds by catViewModel.searchedBreeds.collectAsState()
    val isLoading by catViewModel.isLoading.collectAsState()

    var breedSearch by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BreedSearchBar(
            modifier = Modifier.padding(bottom = 32.dp),
            breed = breedSearch,
            onChangeBreed = {
                coroutineScope.launch {
                    breedSearch = it
                    catViewModel.searchBreed(breedSearch)
                }
            }
        )

        CatsGrid(
            modifier = Modifier.weight(1f),
            catBreeds = if (breedSearch.isEmpty()) catBreeds else searchedBreeds,
            addOrRemoveFromFavourites = catViewModel::addOrRemoveCatFromFavourites,
            loadNextPage = catViewModel::loadNextPage,
            goToDetail = goToDetail
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
    }
}