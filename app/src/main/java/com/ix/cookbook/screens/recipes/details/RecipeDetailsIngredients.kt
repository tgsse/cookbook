package com.ix.cookbook.screens.recipes.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ix.cookbook.R
import com.ix.cookbook.data.models.ExtendedIngredient
import com.ix.cookbook.data.models.dummyRecipe
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

private const val INGREDIENT_CDN_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

@Composable
private fun IngredientItem(ingredient: ExtendedIngredient) {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.typography.bodySmall.color,
        ),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .heightIn(max = 120.dp),
            ) {
                AsyncImage(
                    model = "${INGREDIENT_CDN_URL}${ingredient.image}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    error = painterResource(id = R.drawable.image_placeholder),
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.s),
            ) {
                Text(
                    text = ingredient.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.s))
                Text(
                    text = ingredient.original,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun RecipeDetailsIngredients(ingredients: List<ExtendedIngredient>) {
    LazyColumn(
        contentPadding = PaddingValues(all = MaterialTheme.spacing.m),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.s),
    ) {
        items(ingredients) {
            IngredientItem(it)
        }
    }
}

@Preview
@Composable
fun RecipeDetailsIngredientsPreview() {
    CookbookTheme {
        RecipeDetailsIngredients(ingredients = dummyRecipe.extendedIngredients)
    }
}
