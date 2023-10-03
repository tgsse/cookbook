package com.ix.cookbook.screens.recipes.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.ui.theme.CookbookTheme

@Composable
fun SearchHistory(
    searchHistory: List<String>,
    onItemClick: (String) -> Unit,
) {
    searchHistory.forEach { historyItem ->
        SearchHistoryItem(
            historyItem = historyItem,
            onClick = { onItemClick(historyItem) },
        )
    }
}

@Preview
@Composable
fun SearchHistoryPreview() {
    CookbookTheme {
        SearchHistory(
            searchHistory = listOf("Scooby Snacks"),
            onItemClick = {},
        )
    }
}
