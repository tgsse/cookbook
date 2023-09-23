package com.ix.cookbook.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.ui.theme.CookBookTheme

@Composable
fun FavoriteRecipesScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Text("Favorite Recipes Fragment")
        }
    }
}

@Preview
@Composable
fun FavoriteRecipesPreview() {
    CookBookTheme {
        FavoriteRecipesScreen()
    }
}
