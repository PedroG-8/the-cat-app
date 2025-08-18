package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapps.thecatapp.R
import com.myapps.thecatapp.ui.composables.BreedSearchBar
import com.myapps.thecatapp.ui.composables.CatsGrid
import kotlinx.coroutines.delay
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
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState.canScrollForward) {
        delay(200)
        if (!lazyGridState.canScrollForward) catViewModel.loadPage()
    }

    Box(
        modifier = modifier
            .padding(top = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
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
            if (catBreeds.isEmpty()) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.empty_cats),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            } else {
                CatsGrid(
                    modifier = Modifier.weight(1f),
                    catBreeds = if (breedSearch.isEmpty()) catBreeds else searchedBreeds,
                    addOrRemoveFromFavourites = catViewModel::addOrRemoveCatFromFavourites,
                    lazyGridState = lazyGridState,
                    goToDetail = goToDetail
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp).align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.tertiaryContainer
            )
        }
    }
}