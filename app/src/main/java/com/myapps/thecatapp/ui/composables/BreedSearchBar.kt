package com.myapps.thecatapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapps.thecatapp.R
import com.myapps.thecatapp.app.theme.Transparent

@Composable
fun BreedSearchBar(
    modifier: Modifier = Modifier,
    breed: String,
    onChangeBreed: (String) -> Unit
) {
    TextField(
        value = breed,
        onValueChange = onChangeBreed,
        shape = RoundedCornerShape(100),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedIndicatorColor = Transparent,
            focusedIndicatorColor = Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        placeholder = {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.search_breed),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(100)
                    ).padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_breed),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        },
        modifier = modifier
    )
}