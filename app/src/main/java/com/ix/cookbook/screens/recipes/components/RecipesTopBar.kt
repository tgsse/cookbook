package com.ix.cookbook.screens.recipes.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.data.requestUtil.filters.QueryFilter
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesTopBar(
    selectedQueryFilter: QueryFilter?,
    onQuerySearch: (query: String) -> Unit,
    searchHistory: List<String>,
    onSearchHistoryItemClick: (String) -> Unit,
    onFilterButtonClick: () -> Unit,
    onClearButtonClick: () -> Unit,
) {
    var isSearchBarActive by remember { mutableStateOf(false) }

    if (isSearchBarActive) {
        RecipesSearchBar(
            onQuerySearch = {
                onQuerySearch(it)
                isSearchBarActive = false
            },
            searchHistory = searchHistory,
            onSearchHistoryItemClick = onSearchHistoryItemClick,
            onActiveChange = { isSearchBarActive = it },
            onClearButtonClick = { isSearchBarActive = false },
        )
    } else {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            title = {
                Text(
                    text = selectedQueryFilter?.value
                        ?: stringResource(id = R.string.screen_recipes),
                )
            },
            actions = {
                if (selectedQueryFilter != null) {
                    IconButton(
                        onClick = { onClearButtonClick() },
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
                    onClick = { onFilterButtonClick() },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesSearchBar(
    onQuerySearch: (query: String) -> Unit,
    searchHistory: List<String>,
    onSearchHistoryItemClick: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onClearButtonClick: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchBar(
        modifier = Modifier.focusRequester(focusRequester),
        query = query,
        onQueryChange = {
            if (it.length <= Constants.maxSearchLength) {
                query = it
            }
        },
        onSearch = {
            onQuerySearch(it)
            query = ""
        },
        active = true,
        onActiveChange = onActiveChange,
        trailingIcon = {
            IconButton(
                onClick = {
                    query = ""
                    onClearButtonClick()
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
        SearchHistory(
            searchHistory = searchHistory,
            onItemClick = { onSearchHistoryItemClick(it) },
        )
    }
}

@Preview
@Composable
fun RecipesTopBarPreview() {
    CookbookTheme {
        RecipesTopBar(
            onClearButtonClick = {},
            onFilterButtonClick = {},
            onQuerySearch = { _ -> },
            onSearchHistoryItemClick = {},
            searchHistory = emptyList(),
            selectedQueryFilter = null,
        )
    }
}
