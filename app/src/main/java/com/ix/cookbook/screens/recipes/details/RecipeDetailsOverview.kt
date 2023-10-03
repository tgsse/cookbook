package com.ix.cookbook.screens.recipes.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeLikes
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeMinutes
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeTraits
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@Composable
fun RecipeDetailsOverview(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.BottomCenter,
        )
        {
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                error = painterResource(id = R.drawable.image_placeholder),
                modifier = Modifier
                    .fillMaxSize(),
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(MaterialTheme.spacing.s)
                    .padding(end = MaterialTheme.spacing.m)
                    .alpha(.4f),
            ) {
                RecipeLikes(likes = recipe.aggregateLikes)
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.m))
                RecipeMinutes(readyInMinutes = recipe.readyInMinutes)
            }
        }

        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.m),
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.l))

            Text(
                text = recipe.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.displaySmall,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.m))

            RecipeTraits(recipe)

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.m))

            Text(
                text = "Description",
                modifier = Modifier.alpha(.7f),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.s))
            Text(
                text = recipe.summary,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
fun RecipeDetailsPreview() {
    CookbookTheme {
        RecipeDetailsOverview(dummyRecipe)
    }
}
