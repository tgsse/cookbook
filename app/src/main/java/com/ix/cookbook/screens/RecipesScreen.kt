package com.ix.cookbook.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.ui.theme.CookBookTheme

@Composable
fun RecipesScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text("Recipes Fragment")
    }
}

@Preview
@Composable
fun RecipesPreview() {
    CookBookTheme {
        FoodJokeScreen()
    }
}
