package com.ix.cookbook.screens.recipes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.data.requestUtil.filters.DietTypeFilter
import com.ix.cookbook.data.requestUtil.filters.Filter
import com.ix.cookbook.data.requestUtil.filters.MealTypeFilter
import com.ix.cookbook.data.requestUtil.filters.QueryFilter
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputChips(
    selectedMealFilter: MealTypeFilter?,
    selectedDietFilter: DietTypeFilter?,
    selectedQueryFilter: QueryFilter?,
    onClearFilter: (Filter) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.m),
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.m),
    ) {
        if (selectedMealFilter != null) {
            InputChip(
                selected = true,
                onClick = {
                    onClearFilter(selectedMealFilter)
                },
                label = { Text(selectedMealFilter.label) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.content_desc_remove_filter),
                    )
                },
            )
        }
        if (selectedDietFilter != null) {
            InputChip(
                selected = true,
                onClick = {
                    onClearFilter(selectedDietFilter)
                },
                label = { Text(selectedDietFilter.label) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.content_desc_remove_filter),
                    )
                },
            )
        }
        if (selectedQueryFilter != null) {
            InputChip(
                selected = true,
                onClick = { onClearFilter(selectedQueryFilter) },
                label = { Text("\"$selectedQueryFilter\"") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.content_desc_remove_filter),
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun SearchInputChipsPreview() {
    CookbookTheme {
        SearchInputChips(
            selectedMealFilter = null,
            selectedDietFilter = null,
            selectedQueryFilter = null,
            onClearFilter = {},
        )
    }
}
