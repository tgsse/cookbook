package com.ix.cookbook.screens.recipes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.data.models.Recipe
import com.ix.cookbook.data.models.Recipes
import com.ix.cookbook.data.models.dummyRecipe
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeItem
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@Composable
fun RecipeList(
    recipes: Recipes,
    onItemClick: (Recipe) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(all = MaterialTheme.spacing.m),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.m),
    ) {
        items(recipes.items) {
            RecipeItem(
                modifier = Modifier.clickable {
                    onItemClick(it)
                },
                recipe = it,
            )
        }
    }
}

@Preview
@Composable
private fun RecipeListPreview() {
    CookbookTheme {
        RecipeList(
            recipes = Recipes(items = listOf(dummyRecipe)),
            onItemClick = {},
        )
    }
}
