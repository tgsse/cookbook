package com.ix.cookbook.screens.recipes

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
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
import com.ix.cookbook.screens.joke.FoodJokeScreen
import com.ix.cookbook.screens.recipes.components.NoRecipes
import com.ix.cookbook.screens.recipes.components.RecipeList
import com.ix.cookbook.screens.recipes.components.RecipeListPlaceholder
import com.ix.cookbook.screens.recipes.components.SearchHistoryItem
import com.ix.cookbook.screens.recipes.components.SearchInputChips
import com.ix.cookbook.ui.components.BottomSheet
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.util.Constants.Companion.maxSearchLength
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel = hiltViewModel(),
) {
    // TODO: Refactor into smaller components

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
//                is RecipesViewModel.UiEvent.NavigateToDetail -> {
//                    withContext(dispatchers.main) {
//                        navigator.navigate(TODO("details screen"))
//                    }
//                }
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

    var query by remember { mutableStateOf("") }
    var isSearchBarActive by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (isSearchBarActive) {
                SearchBar(
                    modifier = Modifier.focusRequester(focusRequester),
                    query = query,
                    onQueryChange = {
                        if (it.length <= maxSearchLength) {
                            query = it
                        }
                    },
                    onSearch = {
                        viewModel.onEvent(RecipesEvent.ApplyFilter(queryFilter = QueryFilter(it)))
                        isSearchBarActive = false
                        query = ""
                    },
                    active = true,
                    onActiveChange = { isSearchBarActive = it },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                query = ""
                                isSearchBarActive = false
                            },
                        ) {
                            Icon(
                                Icons.Outlined.Clear,
                                contentDescription = stringResource(
                                    id = R.string.content_desc_clear_search_field,
                                ),
                            )
                        }
                    },
                    placeholder = { Text(stringResource(id = R.string.content_desc_search_recipes)) },
                ) {
                    state.searchHistory.forEach { historyItem ->
                        SearchHistoryItem(
                            historyItem = historyItem,
                            onClick = {
                                viewModel.onEvent(
                                    RecipesEvent.ApplyFilter(
                                        queryFilter = QueryFilter(
                                            historyItem,
                                        ),
                                    ),
                                )
                            },
                        )
                    }
                }

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        Text(
                            text = state.selectedQueryFilter?.value
                                ?: stringResource(id = R.string.screen_recipes),
                        )
                    },
                    actions = {
                        if (state.selectedQueryFilter != null) {
                            IconButton(
                                onClick = { onClearFilter(state.selectedQueryFilter as QueryFilter) },
                                modifier = Modifier,
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(
                                        id = R.string.content_desc_clear_search_results,
                                    ),
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    isSearchBarActive = true
                                },
                                modifier = Modifier,
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = stringResource(
                                        id = R.string.content_desc_search_recipes,
                                    ),
                                )
                            }
                        }
                        IconButton(
                            onClick = { isSheetVisible = !isSheetVisible },
                            modifier = Modifier,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FilterAlt,
                                contentDescription = stringResource(R.string.content_desc_filter_search_results),
                            )
                        }
                    },
                )
            }
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
                    RecipeList(state.recipes)
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
        FoodJokeScreen()
    }
}
