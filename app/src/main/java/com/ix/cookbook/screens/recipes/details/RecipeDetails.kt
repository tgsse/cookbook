package com.ix.cookbook.screens.recipes.details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.data.models.Recipe
import com.ix.cookbook.data.models.dummyRecipe
import com.ix.cookbook.ui.components.BackIconButton
import com.ix.cookbook.ui.components.TopBar
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.CookbookTheme

private enum class DetailsTabs(@StringRes val label: Int) {
    Overview(R.string.label_tab_overview),
    Ingredients(R.string.label_tab_ingredients),
    Instructions(R.string.label_tab_instructions),
}

@Composable
fun RecipeDetails(
    selectedRecipe: Recipe?,
    isFavorite: Boolean,
    onNavigateBack: () -> Unit,
    onFavoriteClick: (Recipe) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableIntStateOf(DetailsTabs.Overview.ordinal) }

    if (selectedRecipe == null) {
        Text("An error occurred. Recipe not found.")
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                TopBar(
                    title = selectedRecipe.title,
                    navigationIcon = {
                        BackIconButton(onClick = { onNavigateBack() })
                    },
                    actions = {
                        FavoriteButton(
                            isFavorite = isFavorite,
                            onClick = {
                                onFavoriteClick(selectedRecipe)
                            },
                        )
                    },
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
            ) {
                TabRow(selectedTabIndex = selectedTab) {
                    DetailsTabs.values().forEach {
                        Tab(
                            selected = selectedTab == it.ordinal,
                            onClick = { selectedTab = it.ordinal },
                            text = {
                                Text(
                                    text = stringResource(id = it.label),
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            },
                        )
                    }
                }
                when (selectedTab) {
                    DetailsTabs.Overview.ordinal -> RecipeDetailsOverview(recipe = selectedRecipe)
                    DetailsTabs.Ingredients.ordinal -> RecipeDetailsIngredients(ingredients = selectedRecipe.extendedIngredients)
                    DetailsTabs.Instructions.ordinal -> RecipeDetailsInstructions(instructions = selectedRecipe.analyzedInstructions)
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
) {
    val image: ImageVector
    val contentDesc: String
    val tint: Color
    if (isFavorite) {
        image = Icons.Outlined.Star
        contentDesc = stringResource(id = R.string.content_desc_favorite)
        tint = Colors.yellow
    } else {
        image = Icons.Outlined.StarBorder
        contentDesc = stringResource(id = R.string.content_desc_unfavorite)
        tint = MaterialTheme.colorScheme.onPrimary
    }

    IconButton(onClick = onClick) {
        Icon(
            imageVector = image,
            contentDescription = contentDesc,
            tint = tint,
        )
    }
}

@Preview
@Composable
fun RecipeDetailsOverviewPreview() {
    CookbookTheme {
        RecipeDetails(
            selectedRecipe = dummyRecipe,
            onNavigateBack = {},
            isFavorite = false,
            onFavoriteClick = { _ -> },
        )
    }
}
