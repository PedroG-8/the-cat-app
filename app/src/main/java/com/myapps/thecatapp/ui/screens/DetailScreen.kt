package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    imageId: String
) {
    val detailViewModel = koinViewModel<DetailViewModel>()
    val cat by detailViewModel.catData.collectAsState()

    LaunchedEffect(Unit) {
        detailViewModel.getCatData(imageId)
    }

    Column(
        modifier = Modifier.fillMaxSize(0.75f)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxHeight(0.75f),
            model = cat?.url,
            contentDescription = null
        )
    }
}