package com.ix.cookbook.screens.recipes.components.recipeitem

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.CookbookTheme

@Composable
fun RecipeMinutes(readyInMinutes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Outlined.AccessTime,
            contentDescription = null,
            tint = Colors.yellow,
        )
        Text(
            text = readyInMinutes.toString(),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Colors.yellow,
            ),
        )
    }
}

@Preview
@Composable
fun RecipeMinutesPreview() {
    CookbookTheme {
        RecipeMinutes(readyInMinutes = 25)
    }
}
