package com.myapps.thecatapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.myapps.thecatapp.domain.model.Cat

@Composable
fun CatsGrid(
    modifier: Modifier = Modifier,
    catBreeds: List<Cat>,
    addOrRemoveFromFavourites: (String) -> Unit = {},
    loadNextPage: () -> Unit = {},
    goToDetail: (String) -> Unit,
    showLifespan: Boolean = false
) {
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState.canScrollForward) {
        if (!lazyGridState.canScrollForward) loadNextPage()
    }

    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(catBreeds) { cat ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable {
                        goToDetail(cat.imageId)
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight(0.75f),
                        model = cat.url,
                        contentDescription = null
                    )
                    Text(text = cat.breed?.name.orEmpty())
                    if (showLifespan) {
                        Text(text = cat.breed?.lifespan?.toString().orEmpty())
                    }
                }
                Icon(
                    imageVector = if (cat.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favourites",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            addOrRemoveFromFavourites(cat.imageId)
                        }
                )
            }
        }
    }
}