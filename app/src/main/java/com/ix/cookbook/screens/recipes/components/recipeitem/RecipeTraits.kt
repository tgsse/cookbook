package com.ix.cookbook.screens.recipes.components.recipeitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.data.models.recipes.Recipe
import com.ix.cookbook.data.models.recipes.dummyRecipe
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeTraits(recipe: Recipe) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.l),
    ) {
        if (recipe.cheap) {
            Trait(label = "Cheap")
        }
        if (recipe.vegan) {
            Trait(label = "Vegan")
        }
        if (recipe.dairyFree) {
            Trait(label = "Dairy Free")
        }
        if (recipe.glutenFree) {
            Trait(label = "Gluten Free")
        }
        if (recipe.veryHealthy) {
            Trait(label = "Very Healthy")
        }
    }
}

@Composable
private fun Trait(label: String) {
    Row {
        Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null, tint = Colors.green)
        Text(
            text = label,
            color = Colors.green,
            modifier = Modifier.padding(start = MaterialTheme.spacing.xs),
        )
    }
}

@Preview
@Composable
fun RecipeTraitsPreview() {
    CookbookTheme {
        RecipeTraits(recipe = dummyRecipe)
    }
}
