package com.myapps.thecatapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myapps.thecatapp.app.theme.White
import com.myapps.thecatapp.domain.model.Cat

@Composable
fun CatsGrid(
    modifier: Modifier = Modifier,
    catBreeds: List<Cat>,
    addOrRemoveFromFavourites: (String) -> Unit = {},
    lazyGridState: LazyGridState,
    goToDetail: (String) -> Unit,
    isFromFavourites: Boolean = false
) {
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
                    .height(200.dp)
                    .clickable { goToDetail(cat.imageId) }
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight(0.75f),
                        model = cat.url,
                        contentDescription = null
                    )
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = cat.breed?.name.orEmpty(),
                            color = White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = if (cat.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favourites",
                            modifier = Modifier.clickable { addOrRemoveFromFavourites(cat.imageId) }.padding(start = 4.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    if (isFromFavourites) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Start),
                            text = "Lifespan | ${cat.breed?.lifespan?.toString().orEmpty()} years",
                            color = White,
                            maxLines = 1,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}