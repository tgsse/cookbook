package com.ix.cookbook.screens.recipes.components.recipeitem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.spacing

@Composable
fun RecipeAttributes(
    likes: Int,
    readyInMinutes: Int,
    vegan: Boolean,
) {
    Row(
        modifier = Modifier
            .padding(bottom = MaterialTheme.spacing.s),
    ) {
        RecipeLikes(likes)
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.m))
        RecipeMinutes(readyInMinutes)
        if (vegan) {
            Icon(
                imageVector = Icons.Filled.Eco,
                contentDescription = null,
                tint = Colors.green,
            )
        }
    }
}
