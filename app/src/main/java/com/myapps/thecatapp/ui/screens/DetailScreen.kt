package com.myapps.thecatapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myapps.thecatapp.R
import com.myapps.thecatapp.app.theme.White
import com.myapps.thecatapp.domain.model.Cat
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    imageId: String,
    goBack: () -> Unit
) {
    val detailViewModel = koinViewModel<DetailViewModel>()
    val cat by detailViewModel.catData.collectAsState()
    val isLoading by detailViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        detailViewModel.getCatData(imageId)
    }

    if (isLoading) {
        LinearProgressIndicator()
    } else if (cat != null) {
        Detail(
            modifier = modifier,
            cat = cat!!,
            addOrRemoveFromFavourites = detailViewModel::addOrRemoveCatFromFavourites,
            goBack = goBack,
        )
    }
}

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    cat: Cat,
    addOrRemoveFromFavourites: () -> Unit,
    goBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 22.dp)
            .padding(top = 48.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .clickable { goBack() }
                .align(Alignment.Start),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 42.dp)
                .padding(bottom = 132.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = cat.url,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cat.breed?.name.orEmpty(),
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    modifier = Modifier.padding(start = 8.dp).clickable { addOrRemoveFromFavourites() },
                    imageVector = if (cat.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favourites),
                    tint = White
                )
            }


            Row(
                modifier = Modifier.offset(x = (-12).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.location),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = cat.breed?.origin.orEmpty(),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                cat.breed?.temperament?.forEach { temperament ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(20.dp),
                        tonalElevation = 2.dp,
                        shadowElevation = 2.dp,
                        modifier = Modifier.padding(4.dp).widthIn(min = 120.dp)
                    ) {
                        Text(
                            text = temperament,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.about),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = cat.breed?.description.orEmpty(),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 14.sp
            )
        }
    }
}
