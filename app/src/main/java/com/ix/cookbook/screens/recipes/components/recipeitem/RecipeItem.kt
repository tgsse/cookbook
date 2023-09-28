package com.ix.cookbook.screens.recipes.components.recipeitem

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ix.cookbook.R
import com.ix.cookbook.data.models.Recipe
import com.ix.cookbook.data.models.dummyRecipe
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

private val gradientColors = listOf(Color.Black, Colors.gray)
val cardHeight = 180.dp

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.typography.bodySmall.color,
        ),
        modifier = Modifier
            .height(cardHeight),
    ) {
        Row {
            AsyncImage(
                model = recipe.image,
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
                    text = recipe.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.s))
                RecipeAttributes(
                    likes = recipe.aggregateLikes,
                    readyInMinutes = recipe.readyInMinutes,
                    vegan = recipe.vegan,
                )
                Text(
                    text = recipe.summary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        brush = Brush.verticalGradient(
                            colors = gradientColors,
                        ),
                    ),
                )
            }
        }
    }
}

@Preview(widthDp = 420)
@Composable
private fun RecipeItemPreview() {
    CookbookTheme {
        RecipeItem(dummyRecipe)
    }
}

@Preview(widthDp = 420)
@Composable
private fun RecipeItemPlaceholderPreview() {
    CookbookTheme {
        RecipeItemPlaceholder()
    }
}
