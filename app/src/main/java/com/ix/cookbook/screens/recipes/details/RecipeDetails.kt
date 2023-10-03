package com.ix.cookbook.screens.recipes.details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ix.cookbook.R
import com.ix.cookbook.screens.recipes.RecipesViewModel
import com.ix.cookbook.ui.theme.CookbookTheme

private enum class DetailsTabs(@StringRes val label: Int) {
    Overview(R.string.label_tab_overview),
    Ingredients(R.string.label_tab_ingredients),
    Instructions(R.string.label_tab_instructions),
}

@Composable
fun RecipeDetails(
    viewModel: RecipesViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableIntStateOf(DetailsTabs.Ingredients.ordinal) }

    val state by viewModel.state.collectAsState()

    if (state.selectedRecipe == null) {
        Text("An error occurred. Recipe not found.")
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                TabRow(selectedTabIndex = selectedTab) {
                    DetailsTabs.values().forEach {
                        Tab(
                            selected = selectedTab == it.ordinal,
                            onClick = { selectedTab = it.ordinal },
                            text = {
                                Text(
                                    text = stringResource(id = it.label),
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            },
                        )
                    }
                }
            },
        ) {
            Column(
                modifier = Modifier.padding(it),
            ) {
                when (selectedTab) {
                    DetailsTabs.Overview.ordinal -> RecipeDetailsOverview(recipe = state.selectedRecipe!!)
                    DetailsTabs.Ingredients.ordinal -> RecipeDetailsIngredients(ingredients = state.selectedRecipe!!.extendedIngredients)
                    DetailsTabs.Instructions.ordinal -> Text("instructions")
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipeDetailsOverviewPreview() {
    CookbookTheme {
        RecipeDetails()
    }
}
