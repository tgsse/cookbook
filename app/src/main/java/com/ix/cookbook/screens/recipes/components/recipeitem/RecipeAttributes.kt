package com.ix.cookbook.screens.recipes.components.recipeitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(end = MaterialTheme.spacing.m),
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(end = MaterialTheme.spacing.m),
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
        if (vegan) {
            Icon(
                imageVector = Icons.Filled.Eco,
                contentDescription = null,
                tint = Colors.green,
            )
        }
    }
}
