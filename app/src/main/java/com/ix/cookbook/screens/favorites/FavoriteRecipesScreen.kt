package com.ix.cookbook.screens.favorites

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.ix.cookbook.R
import com.ix.cookbook.screens.recipes.RecipesEvent
import com.ix.cookbook.screens.recipes.RecipesViewModel
import com.ix.cookbook.screens.recipes.components.ErrorView
import com.ix.cookbook.screens.recipes.components.recipeitem.RecipeItem
import com.ix.cookbook.ui.components.BackIconButton
import com.ix.cookbook.ui.components.TopBar
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteRecipesScreen(
    viewModel: RecipesViewModel = hiltViewModel(), // TODO separate viewModel might be better
    onNavigateToDetails: () -> Unit,
) {
    val screenContentDescription =
        stringResource(id = R.string.content_desc_favorite_recipes_screen)
    val state = viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val lifecycleOwner = LocalLifecycleOwner.current
    val haptics = LocalHapticFeedback.current

    var isSelecting by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableListOf<Int>() }

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
            val title: String
            val navigationIcon: @Composable () -> Unit
            val actions: @Composable (RowScope) -> Unit

            if (!isSelecting) {
                title = stringResource(id = R.string.screen_favorite_recipes)
                navigationIcon = {}
                actions = {}
            } else {
                title = stringResource(
                    id = R.string.label_selected_items_count,
                    selectedItems.size,
                )
                navigationIcon = { BackIconButton(onClick = { selectedItems.clear() }) }
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(
                                RecipesEvent.DeleteFavorites(
                                    selectedItems.toList(),
                                ),
                            )
                            isSelecting = false
                            selectedItems.clear()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteForever,
                            contentDescription = stringResource(R.string.content_desc_remove_favorites),
                        )
                    }
                }
            }

            TopBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
            )
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .semantics {
                    contentDescription = screenContentDescription
                },
            color = MaterialTheme.colorScheme.background,
        ) {
            if (state.value.favoriteRecipes.isEmpty()) {
                ErrorView(error = stringResource(R.string.error_favorites_empty))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(all = MaterialTheme.spacing.m),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.m),
                ) {
                    items(state.value.favoriteRecipes) {
                        val isSelected =
                            isSelecting && selectedItems.contains(it.externalId)
                        RecipeItem(
                            modifier = Modifier
                                .border(
                                    border = BorderStroke(
                                        width = if (isSelected) 4.dp else 0.dp,
                                        color = Color.DarkGray,
                                    ),
                                    shape = CardDefaults.outlinedShape,
                                )
                                .alpha(if (isSelected) .7f else 1f)
                                .combinedClickable(
                                    onClick = {
                                        if (!isSelecting) {
                                            viewModel.onEvent(RecipesEvent.ViewRecipeDetails(it.recipe))
                                        } else {
                                            selectedItems.add(it.externalId)
                                        }
                                    },
                                    onLongClick = {
                                        isSelecting = true
                                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                        selectedItems.add(it.externalId)
                                    },
                                ),
                            recipe = it.recipe,
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
