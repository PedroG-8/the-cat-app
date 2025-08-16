package com.myapps.thecatapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun CatScreen(
    modifier: Modifier = Modifier
) {
    val catViewModel = koinViewModel<CatViewModel>()
    val cat by catViewModel.cat.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                catViewModel.loadCat()
            }
        ) {
            Text("Load random cat")
        }

        cat?.let {
            AsyncImage(
                model = it.url,
                contentDescription = null,
                modifier = Modifier.padding(top = 64.dp)
            )
        }
    }
}