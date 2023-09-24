package com.ix.cookbook.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import com.ix.cookbook.ui.theme.CookBookTheme

@Composable
fun RecipesScreen() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .semantics {
                contentDescription =
                    context.getString(R.string.content_desc_recipes_screen)
            },
        color = MaterialTheme.colorScheme.background,
    ) {
        Text(
            text = stringResource(R.string.screen_recipes),
        )
    }
}

@Preview
@Composable
fun RecipesPreview() {
    CookBookTheme {
        FoodJokeScreen()
    }
}
