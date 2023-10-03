package com.ix.cookbook.screens.recipes.components.recipeitem

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.CookbookTheme

@Composable
fun RecipeLikes(likes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = null,
            tint = Colors.red,
        )
        Text(
            text = likes.toString(),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Colors.red,
            ),
        )
    }
}

@Preview
@Composable
fun RecipeLikesPreview() {
    CookbookTheme {
        RecipeLikes(likes = 23)
    }
}
