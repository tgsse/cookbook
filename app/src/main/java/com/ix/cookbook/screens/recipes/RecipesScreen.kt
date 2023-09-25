package com.ix.cookbook.screens.recipes

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.screens.joke.FoodJokeScreen
import com.ix.cookbook.screens.recipes.components.NoRecipes
import com.ix.cookbook.ui.theme.CookbookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen() {
    val context = LocalContext.current

    fun onFabClick() {
        Log.d("TAG", "click")
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onFabClick() },
                interactionSource = MutableInteractionSource(),
            ) {
                Icon(Icons.Filled.Add, "Add new recipe button.")
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .semantics {
                    contentDescription =
                        context.getString(R.string.content_desc_recipes_screen)
                },
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                Text(
                    text = stringResource(R.string.screen_recipes),
                )

                NoRecipes()
            }
        }
    }
}

@Preview
@Composable
fun RecipesPreview() {
    CookbookTheme {
        FoodJokeScreen()
    }
}
