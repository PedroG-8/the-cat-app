package com.myapps.thecatapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun CatScreen(
    modifier: Modifier = Modifier
) {
    val catViewModel = koinViewModel<CatViewModel>()
    val catBreeds by catViewModel.catBreeds.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                catViewModel.loadCatBreeds()
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Load random cat")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(catBreeds) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f) // keeps items square
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = it.url,
                        contentDescription = null
                    )
                }
            }
        }
    }
}