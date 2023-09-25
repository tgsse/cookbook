package com.ix.cookbook.screens.recipes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeItem
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeItemPlaceholder
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing
import com.valentinilk.shimmer.shimmer

@Composable
fun RecipeList() {
    var isLoading by remember { mutableStateOf(true) }

    Button(onClick = { isLoading = !isLoading }) {
        Text("Got items")
    }

    if (isLoading) {
        LazyColumn(
            modifier = Modifier.shimmer(),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.m),
        ) {
            items(4) {
                RecipeItemPlaceholder()
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.m),
        ) {
            items(20) {
                RecipeItem()
            }
        }
    }
}

@Preview
@Composable
private fun RecipeListPreview() {
    CookbookTheme {
        RecipeList()
    }
}
