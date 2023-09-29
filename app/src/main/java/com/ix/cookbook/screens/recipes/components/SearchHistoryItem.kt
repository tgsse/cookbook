package com.ix.cookbook.screens.recipes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@Composable
fun SearchHistoryItem(
    historyItem: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(all = MaterialTheme.spacing.m)
            .fillMaxWidth(),
    ) {
        IconButton(onClick = { onClick() }) {
            Icon(
                modifier = Modifier.padding(end = MaterialTheme.spacing.s),
                imageVector = Icons.Default.History,
                contentDescription = stringResource(
                    R.string.content_desc_previously_searched_for,
                ),
            )
            Text(text = historyItem)
        }
    }
}

@Preview
@Composable
fun SearchHistoryItemPreview() {
    CookbookTheme {
        SearchHistoryItem(
            historyItem = "Scooby Snacks",
            onClick = {},
        )
    }
}
