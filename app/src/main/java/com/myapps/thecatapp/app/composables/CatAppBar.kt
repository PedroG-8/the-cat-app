package com.myapps.thecatapp.app.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapps.thecatapp.app.Route
import com.myapps.thecatapp.app.theme.White

@Composable
fun CatAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedPage: Route,
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (selectedPage == Route.Home) {
            NavBarIconSelected(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                icon = Icons.Default.Home,
                name = "Home"
            )
        } else {
            Icon(
                modifier = Modifier
                    .clickable { navController.navigate(Route.Home) }
                    .padding(start = 32.dp)
                    .size(32.dp),
                imageVector = Icons.Default.Home,
                contentDescription = "Home Screen",
                tint = White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (selectedPage == Route.Favourites) {
            NavBarIconSelected(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                icon = Icons.Default.Favorite,
                name = "Favourites"
            )
        } else {
            Icon(
                modifier = Modifier
                    .clickable { navController.navigate(Route.Favourites) }
                    .size(32.dp),
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favourites Screen",
                tint = White
            )
        }

    }
}

@Composable
fun NavBarIconSelected(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    name: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp),
            imageVector = icon,
            contentDescription = name,
            tint = White
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 12.sp
        )
    }
}