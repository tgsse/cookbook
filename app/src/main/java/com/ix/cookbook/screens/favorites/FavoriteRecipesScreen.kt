package com.ix.cookbook.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.ix.cookbook.R
import com.ix.cookbook.screens.recipes.RecipesEvent
import com.ix.cookbook.screens.recipes.RecipesViewModel
import com.ix.cookbook.screens.recipes.components.NoRecipes
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeItem
import com.ix.cookbook.ui.components.TopBar
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FavoriteRecipesScreen(
    viewModel: RecipesViewModel = hiltViewModel(), // TODO separate viewModel might be better
    onNavigateToDetails: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.uiEvents.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED,
        ).collect { event ->
            when (event) {
                is RecipesViewModel.UiEvent.NavigateToDetail -> {
                    withContext(Dispatchers.Main) {
                        onNavigateToDetails()
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                title = stringResource(id = R.string.screen_favorite_recipes),
            )
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .semantics {
                    contentDescription =
                        context.getString(R.string.content_desc_food_joke_screen)
                },
            color = MaterialTheme.colorScheme.background,
        ) {
            if (state.value.favoriteRecipes.isEmpty()) {
                NoRecipes(reason = stringResource(R.string.error_favorites_empty))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(all = MaterialTheme.spacing.m),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.m),
                ) {
                    items(state.value.favoriteRecipes) {
                        RecipeItem(
                            recipe = it.recipe,
                            onItemClick = { recipe ->
                                viewModel.onEvent(RecipesEvent.ViewRecipeDetails(recipe))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FavoriteRecipesPreview() {
    CookbookTheme {
        FavoriteRecipesScreen(
            onNavigateToDetails = {},
        )
    }
}
