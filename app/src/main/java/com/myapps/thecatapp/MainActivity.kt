package com.myapps.thecatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.myapps.thecatapp.ui.CatScreen
import com.myapps.thecatapp.ui.theme.TheCatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCatAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}