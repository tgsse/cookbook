package com.ix.cookbook.screens.recipes.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ix.cookbook.R
import com.ix.cookbook.data.models.ExtendedIngredient
import com.ix.cookbook.data.models.dummyRecipe
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@Composable
private fun IngredientItem(ingredient: ExtendedIngredient) {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.typography.bodySmall.color,
        ),
        modifier = Modifier
            .height(120.dp),
    ) {
        Row {
            AsyncImage(
                model = ingredient.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                error = painterResource(id = R.drawable.image_placeholder),
                modifier = Modifier
                    .width(160.dp)
                    .fillMaxHeight(),
            )
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
                    text = pluralStringResource(
                        id = R.plurals.label_ingredient_unit,
                        count = ingredient.amount.toInt(),
                        ingredient.amount,
                        ingredient.unit,
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = ingredient.consistency,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun RecipeDetailsIngredients(ingredients: List<ExtendedIngredient>) {
    ingredients.forEach {
        IngredientItem(it)
    }
}

@Preview
@Composable
fun RecipeDetailsIngredientsPreview() {
    CookbookTheme {
        RecipeDetailsIngredients(ingredients = dummyRecipe.extendedIngredients)
    }
}
