package com.myapps.thecatapp.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            unfocusedIndicatorColor = Transparent,
            focusedIndicatorColor = Transparent
        ),
        placeholder = {
            Text("Search breed")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search breed"
            )
        },
        modifier = modifier
    )
}