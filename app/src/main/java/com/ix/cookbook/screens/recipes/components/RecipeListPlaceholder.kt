package com.ix.cookbook.screens.recipes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeItemPlaceholder
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing
import com.valentinilk.shimmer.shimmer

@Composable
fun RecipeListPlaceholder() {
    LazyColumn(
        modifier = Modifier.shimmer(),
        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.m),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.m),
    ) {
        items(3) {
            RecipeItemPlaceholder()
        }
    }
}

@Preview
@Composable
fun RecipeListPlaceholderPreview() {
    CookbookTheme {
        RecipeListPlaceholder()
    }
}
