package com.ix.cookbook.screens.recipes.components.recipeitem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ix.cookbook.ui.theme.Colors

@Composable
fun RecipeItemPlaceholder() {
    Card(
        modifier = Modifier
            .height(cardHeight)
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.mediumGray),
        )
    }
}
