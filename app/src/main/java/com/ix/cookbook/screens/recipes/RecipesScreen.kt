package com.ix.cookbook.screens.recipes

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.flowWithLifecycle
import com.ix.cookbook.R
import com.ix.cookbook.data.requestUtil.filters.DietTypeFilter
import com.ix.cookbook.data.requestUtil.filters.Filter
import com.ix.cookbook.data.requestUtil.filters.MealTypeFilter
import com.ix.cookbook.data.requestUtil.filters.QueryFilter
import com.ix.cookbook.screens.recipes.components.NoRecipes
import com.ix.cookbook.screens.recipes.components.RecipeList
import com.ix.cookbook.screens.recipes.components.RecipeListPlaceholder
import com.ix.cookbook.screens.recipes.components.RecipesTopBar
import com.ix.cookbook.screens.recipes.components.SearchInputChips
import com.ix.cookbook.ui.components.BottomSheet
import com.ix.cookbook.ui.theme.CookbookTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    navigateToDetails: () -> Unit,
    viewModel: RecipesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val sheetState = rememberModalBottomSheetState()
    var isSheetVisible by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val reloadWhenResumed = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onEvent(event = RecipesEvent.Init)
            }
        }
        lifecycleOwner.lifecycle.addObserver(reloadWhenResumed)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(reloadWhenResumed)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvents.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED,
        ).collect { event ->
            when (event) {
                is RecipesViewModel.UiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }

                is RecipesViewModel.UiEvent.NavigateToDetail -> {
                    withContext(Dispatchers.Main) {
                        navigateToDetails()
                    }
                }
            }
        }
    }

    fun onFabClick() {
        Log.d("TAG", "click")
    }

    fun onFilterSelect(
        mealTypeFilter: MealTypeFilter?,
        dietTypeFilter: DietTypeFilter?,
    ) {
        viewModel.onEvent(
            event = RecipesEvent.ApplyFilter(
                mealFilter = mealTypeFilter,
                dietFilter = dietTypeFilter,
            ),
        )
    }

    fun onSheetDismiss() {
        isSheetVisible = false
    }

    fun onClearFilter(filter: Filter) {
        viewModel.onEvent(
            event = RecipesEvent.ClearFilter(
                filter = filter,
            ),
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            RecipesTopBar(
                selectedQueryFilter = state.selectedQueryFilter,
                onQuerySearch = {
                    viewModel.onEvent(
                        RecipesEvent.ApplyFilter(
                            queryFilter = QueryFilter(
                                it,
                            ),
                        ),
                    )
                },
                searchHistory = state.searchHistory,
                onSearchHistoryItemClick = {
                    viewModel.onEvent(
                        RecipesEvent.ApplyFilter(
                            queryFilter = QueryFilter(it),
                        ),
                    )
                },
                onFilterButtonClick = { isSheetVisible = !isSheetVisible },
                onClearButtonClick = { onClearFilter(state.selectedQueryFilter as QueryFilter) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onFabClick() },
                interactionSource = MutableInteractionSource(),
            ) {
                Icon(Icons.Filled.Add, "Add new recipe button.")
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 0.dp)
                .semantics {
                    contentDescription =
                        context.getString(R.string.content_desc_recipes_screen)
                },
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                SearchInputChips(
                    selectedMealFilter = state.selectedMealFilter,
                    selectedDietFilter = state.selectedDietFilter,
                    selectedQueryFilter = state.selectedQueryFilter,
                    onClearFilter = { filter -> onClearFilter(filter) },
                )

                if (state.isLoading) {
                    RecipeListPlaceholder()
                } else if (state.recipes.items.isEmpty()) {
                    NoRecipes()
                } else {
                    RecipeList(
                        recipes = state.recipes,
                        onItemClick = { viewModel.onEvent(RecipesEvent.ViewRecipeDetails(it)) },
                    )
                }

                if (isSheetVisible) {
                    BottomSheet(
                        mealFilters = defaultMealTypeFilters,
                        dietFilters = defaultDietTypeFilters,
                        activeMealType = state.selectedMealFilter,
                        activeDietType = state.selectedDietFilter,
                        sheetState = sheetState,
                        onDismissRequest = { onSheetDismiss() },
                        onSubmit = { mealType, dietType ->
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    isSheetVisible = false
                                }
                            }
                            onFilterSelect(mealType, dietType)
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipesPreview() {
    CookbookTheme {
        RecipesScreen(navigateToDetails = {})
    }
}
